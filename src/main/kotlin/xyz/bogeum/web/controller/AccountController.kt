package xyz.bogeum.web.controller

import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import xyz.bogeum.auth.JwtProvider
import xyz.bogeum.util.logger
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/user")
class AccountController(
    private val jwtProvider: JwtProvider
) {

    val log = logger()

    @GetMapping("/verify")
    fun verify(resp: HttpServletResponse) { resp.status = HttpStatus.OK.value() }

    @GetMapping("/test")
    fun test(req: HttpServletRequest) {
        log.info("/user/test()")
        log.info("auth: ${SecurityContextHolder.getContext().authentication}")
        val token = jwtProvider.getTokenFromCookie(req)

        if (token != null)
            log.info("id: ${jwtProvider.getId(token)}")
        else
            log.info("id: null")
    }

}