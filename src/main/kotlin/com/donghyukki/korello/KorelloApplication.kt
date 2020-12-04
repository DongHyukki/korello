package com.donghyukki.korello

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KorelloApplication

fun main(args: Array<String>) {
    runApplication<KorelloApplication>(*args)
}
