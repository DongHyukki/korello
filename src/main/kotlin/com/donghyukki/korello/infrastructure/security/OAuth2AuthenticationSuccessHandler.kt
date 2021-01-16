package com.donghyukki.korello.infrastructure.security

import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class OAuth2AuthenticationSuccessHandler : AuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        val oAuth2User = authentication?.principal as DefaultOAuth2User
        val accessToken = oAuth2User.attributes["accessToken"].toString()
        val refreshToken = oAuth2User.attributes["refreshToken"].toString()

        response?.sendRedirect("http://korello.s3-website.ap-northeast-2.amazonaws.com?accessToken=${accessToken}&refreshToken=${refreshToken}")
    }
}