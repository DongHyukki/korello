package com.donghyukki.korello

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
class KorelloApplication

fun main(args: Array<String>) {
    runApplication<KorelloApplication>(*args)
}
