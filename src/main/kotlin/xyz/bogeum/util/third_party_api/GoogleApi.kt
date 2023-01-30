package xyz.bogeum.util.third_party_api

import org.json.JSONObject
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import xyz.bogeum.auth.Secret
import xyz.bogeum.exception.GoogleApiException
import xyz.bogeum.util.HttpConnectionUtil
import xyz.bogeum.util.logger
import java.net.HttpURLConnection
import java.net.InetAddress
import java.net.URL

@Component
class GoogleApi(
    private val secret: Secret,
    private val httpConnectionUtil: HttpConnectionUtil
) {
    private val log = logger()

    // https://developers.google.com/identity/protocols/oauth2/web-server#exchange-authorization-code
    fun getAccessToken(code: String): String {

        val redirectUri = if (isLocalhost()) "http://localhost:8080/afterlogin&"
            else "https://bogeum.xyz/afterlogin&"

        val url = URL("https://oauth2.googleapis.com/token")
        val params = "code=$code&" +
                "client_id=${secret.googleClientId}&" +
                "client_secret=${secret.googleSecret}&" +
                "redirect_uri=$redirectUri" +
                "grant_type=authorization_code"

        val conn = httpConnectionUtil.getPostConnection(url, params.toByteArray())
        val respJson = JSONObject(httpConnectionUtil.getResponse(conn))
        log.info(respJson.toString())

        return if (respJson.has("access_token")) respJson.getString("access_token")
            else throw GoogleApiException(respJson.getString("error"))
    }

    fun getEmail(tk: String): String {
        val url = URL("https://www.googleapis.com/oauth2/v2/userinfo")
        val conn = url.openConnection() as HttpURLConnection
        conn.setRequestProperty("Authorization", "Bearer $tk")

        val respJson = JSONObject(httpConnectionUtil.getResponse(conn))

        return if (respJson.has("email")) respJson.getString("email")
            else throw GoogleApiException(respJson.getString("error"))
    }

    private fun isLocalhost():Boolean {
        val ipAddr = InetAddress.getLocalHost().hostAddress.split(".")
        log.info("IPv4: ${ipAddr.joinToString(".")}")
        return (ipAddr[0] == "192" && ipAddr[1] == "168") || (ipAddr[0] == "26" && ipAddr[1] == "146")
    }
}