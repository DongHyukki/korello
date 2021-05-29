package com.donghyukki.korello.presentation.controller.auth

import com.donghyukki.korello.application.services.member.MemberCrudService
import com.donghyukki.korello.infrastructure.exception.KorelloExpiredTokenException
import com.donghyukki.korello.infrastructure.exception.KorelloIllegalTokenException
import com.donghyukki.korello.infrastructure.exception.KorelloNullTokenException
import com.donghyukki.korello.infrastructure.security.service.TokenService
import com.donghyukki.korello.presentation.dto.MemberDTO
import com.donghyukki.korello.presentation.dto.response.KorelloResponse
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController


@RestController
class AuthController(
    private val memberCrudService: MemberCrudService,
    private val tokenService: TokenService
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
            val claims = tokenService.getClaim(refreshToken)
            val providerId = claims[CLAIM_PROVIDER_KEY].toString()
            val name = claims[CLAIM_NAME_KEY].toString()
            val newAccessToken = tokenService.createAccessToken(providerId, name)
            val newRefreshToken = tokenService.createRefreshToken(providerId, name)

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


