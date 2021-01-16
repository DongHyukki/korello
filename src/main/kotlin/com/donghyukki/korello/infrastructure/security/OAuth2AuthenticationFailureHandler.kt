package com.donghyukki.korello.infrastructure.security

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class OAuth2AuthenticationFailureHandler: AuthenticationFailureHandler {

    override fun onAuthenticationFailure(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        exception: AuthenticationException?
    ) {
        println("1222")
        exception?.printStackTrace()
    }
}