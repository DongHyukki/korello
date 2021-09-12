package com.donghyukki.korello

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.scheduling.annotation.EnableAsync

@EnableJpaAuditing
@SpringBootApplication
class KorelloApplication

fun main(args: Array<String>) {
    runApplication<KorelloApplication>(*args)
}
