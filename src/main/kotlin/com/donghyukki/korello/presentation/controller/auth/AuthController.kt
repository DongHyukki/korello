package com.donghyukki.korello.presentation.controller.auth

import com.donghyukki.korello.application.client.OAuthClient
import com.donghyukki.korello.presentation.dto.response.KorelloResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController {

    @GetMapping("api/v1/auth/authorize")
    fun auth(@RequestParam code: String): KorelloResponse {
        val userToken = OAuthClient.getUserToken(code)
        return KorelloResponse()
    }
}