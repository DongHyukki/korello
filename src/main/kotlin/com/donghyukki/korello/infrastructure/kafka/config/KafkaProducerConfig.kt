package com.donghyukki.korello.infrastructure.kafka.config

import com.donghyukki.korello.presentation.dto.EventDTO
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.serializer.JsonSerializer


@Configuration
class KafkaProducerConfig(
    private val kafkaPropertyConfig: KafkaPropertyConfig
) {
    @Bean
    fun initProducerFactory(): DefaultKafkaProducerFactory<String, EventDTO> {
        return DefaultKafkaProducerFactory<String, EventDTO>(getProducerConfig())
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, EventDTO> {
        return KafkaTemplate(initProducerFactory())
    }

    private fun getProducerConfig(): Map<String, Any> {
        return mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaPropertyConfig.kafkaAddress,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java
        )
    }
}