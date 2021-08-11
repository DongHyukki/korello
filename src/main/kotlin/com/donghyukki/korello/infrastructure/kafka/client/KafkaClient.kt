package com.donghyukki.korello.infrastructure.kafka.client

import com.donghyukki.korello.infrastructure.kafka.config.KafkaPropertyConfig
import com.donghyukki.korello.presentation.dto.EventDTO
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaClient(
    private val kafkaTemplate: KafkaTemplate<String, EventDTO>,
    private val kafkaPropertyConfig: KafkaPropertyConfig,
) {
    private val kafkaLogger = LoggerFactory.getLogger("KAFKA_LOGGER")

    fun sendAsyncMessage(eventDTO: EventDTO) {
        val future = kafkaTemplate.send(kafkaPropertyConfig.topicName, eventDTO)
        future.addCallback(
            { result -> kafkaLogger.info("kafka publish success {}", result?.recordMetadata?.offset()) },
            { ex -> ex.printStackTrace() })
    }
}
