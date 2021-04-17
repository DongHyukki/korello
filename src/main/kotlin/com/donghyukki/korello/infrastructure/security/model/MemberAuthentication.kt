package com.donghyukki.korello.infrastructure.security.model

import org.springframework.context.annotation.Configuration
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder

@Configuration
class MemberAuthentication : AuthenticationFacade {

    override fun getAuthentication(): Authentication {
        return SecurityContextHolder.getContext().authentication
    }

    fun getMemberId(): Long {
        val principal = getAuthentication().principal as Map<*, *>
        return principal["id"] as Long
    }

    fun getMemberName(): String {
        val principal = getAuthentication().principal as Map<*, *>
        return principal["name"] as String
    }

}

interface AuthenticationFacade {
    fun getAuthentication(): Authentication
}
