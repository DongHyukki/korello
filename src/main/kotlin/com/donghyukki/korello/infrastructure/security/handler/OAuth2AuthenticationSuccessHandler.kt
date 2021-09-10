package com.donghyukki.korello.infrastructure.security.handler

import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class OAuth2AuthenticationSuccessHandler(
    private val profile: String
) : AuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        val oAuth2User = authentication?.principal as DefaultOAuth2User
        val accessToken = oAuth2User.attributes["accessToken"].toString()
        val refreshToken = oAuth2User.attributes["refreshToken"].toString()

        when(profile) {
            "local" -> response?.sendRedirect("http://localhost:8082?accessToken=${accessToken}")
            "dev" -> response?.sendRedirect("https://korello.app?accessToken=${accessToken}&refreshToken=${refreshToken}")
        }
    }
}
