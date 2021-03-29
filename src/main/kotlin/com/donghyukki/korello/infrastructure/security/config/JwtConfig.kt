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

    private companion object {
        const val CLAIM_PROVIDER_KEY = "providerId"
        const val CLAIM_NAME_KEY = "name"
    }

    @Value("\${hyuki-secret-key}")
    lateinit var secretKey: String

    @Value("\${hyuki-access-expire-hour}")
    lateinit var expireHour: String

    @Value("\${hyuki-refresh-expire-day}")
    lateinit var expireDay: String

    fun createAccessToken(providerId: String, name: String): String {
        val claim = mapOf<String, Any>(CLAIM_PROVIDER_KEY to providerId, CLAIM_NAME_KEY to name)
        return Jwts.builder()
            .setClaims(claim)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .setExpiration(Date.from(ZonedDateTime.now().plusHours(expireHour.toLong()).toInstant()))
//            .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(expireHour.toLong()).toInstant()))
            .compact()
    }

    fun createRefreshToken(providerId: String, name: String): String {
        val claim = mapOf<String, Any>(CLAIM_PROVIDER_KEY to providerId, CLAIM_NAME_KEY to name)
        return Jwts.builder()
            .setClaims(claim)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .setExpiration(Date.from(ZonedDateTime.now().plusDays(expireDay.toLong()).toInstant()))
            .compact()
    }

    fun getClaim(token: String): Claims {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body
    }

}