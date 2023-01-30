package xyz.bogeum.util

import org.springframework.stereotype.Component
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CookieUtil {
    fun setCookie(resp: HttpServletResponse, key: String, value: String) = resp.addCookie(makeCookie(key, value))

    fun deleteCookie(resp: HttpServletResponse, key: String) = resp.addCookie(makeCookie(key, null))

    fun getCookie(request: HttpServletRequest, key: String): Cookie? {
        val cookies = request.cookies
        if (cookies != null) {
            return cookies.find { it.name == key }
        }

        return null
    }

    private fun makeCookie(key: String, value: String?)
    = Cookie(key, value).also {
        it.path = "/"
        it.secure = true
        it.isHttpOnly = true
        it.domain = "localhost"
    }
}