package com.donghyukki.korello.domain.member.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class RoleTest {

    @Test
    fun roleTest() {
        val valueOf = Role.valueOf()
        valueOf.toAuthorities()
    }
}