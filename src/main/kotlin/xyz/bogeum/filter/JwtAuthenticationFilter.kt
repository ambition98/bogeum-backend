package xyz.bogeum.filter

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import xyz.bogeum.auth.JwtProvider
import xyz.bogeum.auth.JwtState
import xyz.bogeum.util.logger
import xyz.bogeum.web.service.AccountServ
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationFilter(
    private val jwtProvider: JwtProvider,
    private val accountServ: AccountServ
) : OncePerRequestFilter() {

    private val log = logger()

    override fun doFilterInternal(req: HttpServletRequest, resp: HttpServletResponse, filterChain: FilterChain) {
        var jwt = jwtProvider.getTokenFromCookie(req)
        log.info("jwt: $jwt")

        if (jwt != null && jwt.isNotEmpty()) {
            if (jwtProvider.getState(jwt) == JwtState.EXPIRED) {
                jwt = accountServ.refreshAccessToken(req, resp)
            }
        }

        val authentication = jwtProvider.makeAuthentication(jwt)
        log.info("Auth: $authentication")
        SecurityContextHolder.getContext().authentication = authentication

        filterChain.doFilter(req, resp)
    }
}