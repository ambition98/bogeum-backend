//package xyz.bogeum.web.controller
//
//import org.springframework.context.event.EventListener
//import org.springframework.scheduling.annotation.Async
//import org.springframework.stereotype.Component
//import xyz.bogeum.util.logger
//
//@Component
//class TestEventHandler {
//
//    private final val log = logger()
//
//    @Async
//    @EventListener
//    fun firstEvent(testEvent: TestEvent) {
//        Thread.sleep(1000)
//        log.info("Executed firstEvent(), name: ${testEvent.name}")
//    }
//
//    @Async
//    @EventListener
//    fun secondEvent(testEvent: TestEvent) {
//        Thread.sleep(2000)
//        log.info("Executed secondEvent(), name: ${testEvent.name}")
//    }
//}