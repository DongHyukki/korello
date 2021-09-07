package com.donghyukki.korello.application.port

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication

interface AuthenticationFacade {
    fun getAuthentication(): Authentication
    fun getMemberId(): Long
    fun getMemberName(): String
    fun of(token: String): UsernamePasswordAuthenticationToken
    fun getTestAuthentication(): UsernamePasswordAuthenticationToken
    fun getAnonymousAuthentication(): UsernamePasswordAuthenticationToken
}
