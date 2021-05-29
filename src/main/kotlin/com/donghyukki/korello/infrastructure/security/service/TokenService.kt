package com.donghyukki.korello.infrastructure.security.service

import com.donghyukki.korello.infrastructure.security.config.JwtConfig
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Service
import java.time.ZonedDateTime
import java.util.*

@Service
class TokenService(
    private val jwtConfig: JwtConfig
) {

    private companion object {
        const val CLAIM_PROVIDER_KEY = "providerId"
        const val CLAIM_NAME_KEY = "name"
    }

    fun createAccessToken(providerId: String, name: String): String {
        val claim = mapOf<String, Any>(CLAIM_PROVIDER_KEY to providerId, CLAIM_NAME_KEY to name)
        return Jwts.builder()
            .setClaims(claim)
            .signWith(SignatureAlgorithm.HS256, jwtConfig.secretKey)
            .setExpiration(Date.from(ZonedDateTime.now().plusHours(jwtConfig.expireHour.toLong()).toInstant()))
//            .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(jwtConfig.expireHour.toLong()).toInstant()))
            .compact()
    }

    fun createRefreshToken(providerId: String, name: String): String {
        val claim = mapOf<String, Any>(CLAIM_PROVIDER_KEY to providerId, CLAIM_NAME_KEY to name)
        return Jwts.builder()
            .setClaims(claim)
            .signWith(SignatureAlgorithm.HS256, jwtConfig.secretKey)
            .setExpiration(Date.from(ZonedDateTime.now().plusDays(jwtConfig.expireDay.toLong()).toInstant()))
            .compact()
    }

    fun getClaim(token: String): Claims {
        return Jwts.parser().setSigningKey(jwtConfig.secretKey).parseClaimsJws(token).body
    }

}