package com.donghyukki.korello.infrastructure.kafka.config

import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaAdmin

@Configuration
class KafkaTopicConfig(
    private val kafkaPropertyConfig: KafkaPropertyConfig
) {
    @Bean
    fun kafkaAdmin(): KafkaAdmin {
        return KafkaAdmin(mapOf(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaPropertyConfig.kafkaAddress))
    }

    @Bean
    fun initTopic(): NewTopic {
        return NewTopic(kafkaPropertyConfig.topicName, kafkaPropertyConfig.numPartitions.toInt(), kafkaPropertyConfig.replicationFactor.toShort())
    }
}