package xyz.bogeum.filter

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import xyz.bogeum.auth.JwtProvider
import xyz.bogeum.util.logger
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationFilter(
    private val jwtProvider: JwtProvider
) : OncePerRequestFilter() {

    private val log = logger()

    override fun doFilterInternal(req: HttpServletRequest, resp: HttpServletResponse, filterChain: FilterChain) {
//        req.cookies.forEach {
//            println("key: ${it.name}, value: ${it.value}")
//        }
        val tk = jwtProvider.getTokenFromCookie(req)
        log.info("jwt: $tk")

        if (tk != null && tk.isNotEmpty()) {
            val authentication = try {
                jwtProvider.makeAuthentication(tk)
            } catch (e: Exception) {
                null
            }

            SecurityContextHolder.getContext().authentication = authentication
            log.info("auth: $authentication")
        }

        filterChain.doFilter(req, resp)
    }
}