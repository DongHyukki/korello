package com.donghyukki.korello.presentation.controller.auth

import com.donghyukki.korello.domain.member.service.MemberCrudService
import com.donghyukki.korello.infrastructure.exception.*
import com.donghyukki.korello.infrastructure.security.config.JwtConfig
import com.donghyukki.korello.presentation.dto.MemberDTO
import com.donghyukki.korello.presentation.dto.response.KorelloResponse
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.web.bind.annotation.*


@RestController
class AuthController(
    private val memberCrudService: MemberCrudService,
    private val jwtConfig: JwtConfig
) {
    private companion object {
        const val AUTHORIZATION_HEADER_KEY = "authorization"
        const val BEARER = "Bearer"
        const val CLAIM_PROVIDER_KEY = "providerId"
        const val CLAIM_NAME_KEY = "name"
        const val RESPONSE_ACCESS_TOKEN_KEY = "access_token"
        const val RESPONSE_REFRESH_TOKEN_KEY = "refresh_token"
    }

    @PostMapping("oauth2/refresh")
    fun auth(@RequestHeader headers: Map<String, String>): KorelloResponse {
        val refreshToken = getTokenFromHeader(headers)

        try {
            val claims = jwtConfig.getClaim(refreshToken)
            val providerId = claims[CLAIM_PROVIDER_KEY].toString()
            val name = claims[CLAIM_NAME_KEY].toString()
            val newAccessToken = jwtConfig.createAccessToken(providerId, name)
            val newRefreshToken = jwtConfig.createRefreshToken(providerId, name)

            memberCrudService.changeAuth(MemberDTO.Companion.Update(providerId, name, newAccessToken, newRefreshToken))

            return KorelloResponse(
                hashMapOf(
                    RESPONSE_ACCESS_TOKEN_KEY to newAccessToken,
                    RESPONSE_REFRESH_TOKEN_KEY to newRefreshToken
                )
            )
        } catch (e: ExpiredJwtException) {
            throw KorelloExpiredTokenException()
        } catch (e: Exception) {
            throw KorelloIllegalTokenException()
        }

    }

    private fun getTokenFromHeader(headers: Map<String, String>): String {
        return headers[AUTHORIZATION_HEADER_KEY]?.split(BEARER)?.get(1)?.trim() ?: throw KorelloNullTokenException()
    }

}


