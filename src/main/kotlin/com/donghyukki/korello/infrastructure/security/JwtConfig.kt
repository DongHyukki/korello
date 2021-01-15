package com.donghyukki.korello.infrastructure.security

import com.fasterxml.jackson.databind.ObjectMapper
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

    fun createAccessToken(providerId: String, name: String): String {
        val subject = hashMapOf("providerId" to providerId, "name" to name)
        return Jwts.builder()
            .setSubject(ObjectMapper().writeValueAsString(subject))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .setExpiration(Date.from(ZonedDateTime.now().plusHours(expireHour.toLong()).toInstant()))
            .compact()
    }

    fun createRefreshToken(providerId: String, name: String): String {
        val subject = hashMapOf("providerId" to providerId, "name" to name)
        return Jwts.builder()
            .setSubject(ObjectMapper().writeValueAsString(subject))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .setExpiration(Date.from(ZonedDateTime.now().plusDays(expireDay.toLong()).toInstant()))
            .compact()
    }


}