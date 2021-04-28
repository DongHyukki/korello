package com.donghyukki.korello.domain.member.model

import org.springframework.security.core.authority.SimpleGrantedAuthority

enum class Role(
    val value: String,
) {
    USER("ROLE_USER"),
    ANONYMOUS("ROLE_ANONYMOUS")
    ;

    fun toAuthorities() = mutableListOf(SimpleGrantedAuthority(this.value))

    companion object {
        fun toAuthorities(value: String) = mutableListOf(SimpleGrantedAuthority(valueOf(value).value))
        fun toAuthorities(role: Role) = mutableListOf(SimpleGrantedAuthority(role.value))
    }
}