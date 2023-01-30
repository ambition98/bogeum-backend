package xyz.bogeum.web.controller

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import xyz.bogeum.util.CookieUtil
import xyz.bogeum.util.logger
import javax.servlet.http.HttpServletRequest

@RestController
class TestController(
    private val cookieUtil: CookieUtil,
) {

    private final val encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    val log = logger()

    @GetMapping("/test")
    fun getTest() {
//        log.info("Get Test")
//        log.info("auth: ${SecurityContextHolder.getContext().authentication}")
        log.info(encoder.encode("a"))
        log.info(encoder.encode("aaa"))
    }

    @PostMapping("/test")
    fun postTest(req: HttpServletRequest) {
        log.info("Post Test")
        req.cookies.forEach {
            println("k: ${it.name}, v: ${it.value} ${it.maxAge} ${it.path} ${it.comment} ${it.domain} ${it.isHttpOnly} ${it.secure} ${it.version} ")
        }
//        log.info("cookie: ${cookieUtil.getCookie(req, "tk")?.value}")
        log.info("auth: ${SecurityContextHolder.getContext().authentication}")
    }
}