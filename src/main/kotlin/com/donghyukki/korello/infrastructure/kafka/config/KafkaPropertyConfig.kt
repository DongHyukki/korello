package com.donghyukki.korello.infrastructure.kafka.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@PropertySource("classpath:/application-kafka.properties")
class KafkaPropertyConfig {

    @Value("\${kafka.bootstrapAddress}")
    lateinit var kafkaAddress: String

    @Value("\${kafka.topic.name}")
    lateinit var topicName: String

    @Value("\${kafka.partition.num}")
    lateinit var numPartitions: String

    @Value("\${kafka.replication.factor}")
    lateinit var replicationFactor: String
}