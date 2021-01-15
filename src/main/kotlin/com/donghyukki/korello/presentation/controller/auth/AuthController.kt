package com.donghyukki.korello.presentation.controller.auth

import com.donghyukki.korello.application.client.OAuthClient
import com.donghyukki.korello.domain.member.service.MemberCrudService
import com.donghyukki.korello.presentation.dto.response.KorelloResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
class AuthController(
    private val memberCrudService: MemberCrudService
) {

    @GetMapping("api/v1/auth/authorize")
    fun auth(@RequestParam code: String): KorelloResponse {
        val userAuth = OAuthClient.getMemberAuth(code)
        val userInfo = OAuthClient.getMemberInfo(userAuth)
        return KorelloResponse()
    }

}


