package xyz.bogeum.filter

import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import xyz.bogeum.util.logger
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AccessControlFilter : OncePerRequestFilter() {

    val log = logger()

    override fun doFilterInternal(
        req: HttpServletRequest, resp: HttpServletResponse, filterChain: FilterChain
    ) {
        log.info("Request about ${req.remoteAddr} -> ${req.method} ${req.requestURI}")
        filterChain.doFilter(req, resp)
    }
}