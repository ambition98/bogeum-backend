package xyz.bogeum.auth

import org.json.JSONObject
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import xyz.bogeum.util.FileUtil

@Component
class Secret(
    @Value("\${secret.path}")
    private val secretPath: String
) {
    private val secret: JSONObject = JSONObject(FileUtil.get(secretPath))

    val googleClientId: String = secret.getJSONObject("oauth")
        .getJSONObject("google")
        .getString("client_id")

    val googleSecret: String = secret.getJSONObject("oauth")
        .getJSONObject("google")
        .getString("secret")

    val tokenKey: String = secret.getString("token_key")

    override fun toString()
    = "googleClientId: $googleClientId," +
            " googleSecret: $googleSecret," +
            " tokenKey: $tokenKey"
}