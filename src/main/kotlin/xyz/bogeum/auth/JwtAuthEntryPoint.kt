package xyz.bogeum.auth

import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import xyz.bogeum.util.logger
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthEntryPoint : AuthenticationEntryPoint {

    val log = logger()

    override fun commence(
        req: HttpServletRequest,
        resp: HttpServletResponse,
        authException: AuthenticationException
    ) {
        log.warn("Invalid Jwt")
        val out = resp.writer
        resp.setHeader("Content-Type", "application/json")
        resp.status = HttpStatus.BAD_REQUEST.value()
        val response = "{httpStatus: ${HttpStatus.BAD_REQUEST}, msg: Need Login"

        out.print(response)
        out.flush()
        out.close()
    }
}