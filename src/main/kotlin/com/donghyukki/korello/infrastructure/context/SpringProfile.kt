package com.donghyukki.korello.infrastructure.context

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class SpringProfile(
) {
    @Value("\${spring.profiles.active:}")
    lateinit var active: String
}