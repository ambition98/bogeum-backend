package xyz.bogeum.web.controller

import org.springframework.context.ApplicationEventPublisher
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import xyz.bogeum.util.logger
import xyz.bogeum.web.service.KafkaProducer

@RestController
class TestController(
    private val publisher: ApplicationEventPublisher,
    private val kafkaProducer: KafkaProducer
) {

    private final val encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
    private final val log = logger()

    @GetMapping("/kafkatest")
    fun kafkaTest() {
        log.info("/kafkatest")
        kafkaProducer.sendVerifyEmail("ambition65@naver.com", "1234")
    }

//    @GetMapping("/eventtest")
//    fun getTest(name: String) {
//        log.info("/test, name: $name")
//        testEventMethod(name)
//    }
//
//    private fun testEventMethod(name: String) {
//        publisher.publishEvent(TestEvent(name))
//    }
//
//    @PostMapping("/test")
//    fun postTest(req: HttpServletRequest) {
//        log.info("Post Test")
//        req.cookies.forEach {
//            println("k: ${it.name}, v: ${it.value} ${it.maxAge} ${it.path} ${it.comment} ${it.domain} ${it.isHttpOnly} ${it.secure} ${it.version} ")
//        }
////        log.info("cookie: ${cookieUtil.getCookie(req, "tk")?.value}")
//        log.info("auth: ${SecurityContextHolder.getContext().authentication}")
//    }
}