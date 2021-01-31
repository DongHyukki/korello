package com.donghyukki.korello.infrastructure.security.model

import org.springframework.context.annotation.Configuration
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder

@Configuration
class MemberAuthentication : AuthenticationFacade {

    override fun getAuthentication(): Authentication {
        return SecurityContextHolder.getContext().authentication
    }

}

interface AuthenticationFacade {
    fun getAuthentication(): Authentication
}
