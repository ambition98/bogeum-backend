package xyz.bogeum.web.controller

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class TestKafkaConsumer {

    @KafkaListener(topics = ["quickstart-events"], groupId = "group1")
    fun consume(msg: String) {
        println("msg: $msg")
    }
}