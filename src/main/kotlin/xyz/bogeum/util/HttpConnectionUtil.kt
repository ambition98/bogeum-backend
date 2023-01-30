package xyz.bogeum.util

import org.springframework.stereotype.Component
import xyz.bogeum.exception.GoogleApiException
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

@Component
class HttpConnectionUtil {
    private val log = logger()

    fun getPostConnection(url: URL, postData: ByteArray) : HttpURLConnection {
        val conn = url.openConnection() as HttpURLConnection
        conn.doOutput = true
//            conn.instanceFollowRedirects = false
        conn.requestMethod = "POST"
        conn.setRequestProperty("charset", "utf-8")
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
        conn.setRequestProperty("Content-Length", postData.size.toString())
        conn.useCaches = false

        val output = DataOutputStream(conn.outputStream)
        output.write(postData)
        output.flush()
        output.close()

        return conn
    }

    fun getResponse(conn: HttpURLConnection): String {
        if (conn.responseCode != 200)
            return getResponseFromConnection(conn.errorStream)

        return getResponseFromConnection(conn.inputStream)
    }

    private fun getResponseFromConnection(input: InputStream): String {
        val br = BufferedReader(InputStreamReader(input))
        val sb = StringBuilder()
        var line: String?
        while (br.readLine().also { line = it } != null) {
            sb.append(line)
        }

        return sb.toString()
    }
}