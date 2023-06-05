package xyz.bogeum.web.service

import org.json.JSONObject
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import xyz.bogeum.enum.KafkaTopic
import xyz.bogeum.util.logger

@Service
class KafkaProducer(
    private val kafkaTemplate: KafkaTemplate<String, Any>
) {
    private final val log = logger()

    fun sendVerifyEmail(email: String, code: String): Boolean {

        log.info("")
        val json = JSONObject()
        json.put("email", email)
        json.put("code", code)

        try {
            kafkaTemplate.send(KafkaTopic.SENDMAIL.topicName, json.toString())
        } catch (e: Exception) {
            log.info("Fail to publish kafka Msg: ${e.message} [${KafkaTopic.SENDMAIL.topicName}] $json")
            return false
        }
        log.info("Published kafka Msg: [${KafkaTopic.SENDMAIL.topicName}] $json")
        return true
    }
}