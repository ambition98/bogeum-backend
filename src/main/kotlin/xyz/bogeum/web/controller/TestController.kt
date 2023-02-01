package xyz.bogeum.web.controller

import org.springframework.context.ApplicationEventPublisher
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import xyz.bogeum.util.CookieUtil
import xyz.bogeum.util.logger
import javax.servlet.http.HttpServletRequest

@RestController
class TestController(
    private val publisher: ApplicationEventPublisher
) {

    private final val encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
    private final val log = logger()

    @GetMapping("/test")
    fun getTest(name: String) {
        log.info("/test, name: $name")
        testEventMethod(name)
    }

    private fun testEventMethod(name: String) {
        publisher.publishEvent(TestEvent(name))
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