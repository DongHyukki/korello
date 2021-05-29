package com.donghyukki.korello.infrastructure.security.config

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import java.time.ZonedDateTime
import java.util.*


@Configuration
@PropertySource("classpath:/application-secret.properties")
class JwtConfig {

    @Value("\${hyuki-secret-key}")
    lateinit var secretKey: String

    @Value("\${hyuki-access-expire-hour}")
    lateinit var expireHour: String

    @Value("\${hyuki-refresh-expire-day}")
    lateinit var expireDay: String

}