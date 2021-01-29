package com.donghyukki.korello.infrastructure.security

import com.donghyukki.korello.KorelloApplicationTests
import com.donghyukki.korello.infrastructure.security.config.JwtConfig
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class JwtConfigTest(
    @Autowired private val jwtConfig: JwtConfig
) : KorelloApplicationTests() {

    @Test
    fun createAccessTokenTest() {
        jwtConfig.createAccessToken("test", "12345")

    }
}

