package com.donghyukki.korello.presentation.dto

import com.donghyukki.korello.domain.member.model.Member
import com.donghyukki.korello.domain.member.model.Role

class MemberDTO {

    companion object {
        data class Create(
            val name: String,
            val role: Role,
            val providerId: String,
            val registrationId: String,
            val accessToken: String,
            val refreshToken: String
        ) {
            fun toEntity() = Member(name, role, providerId, registrationId, accessToken, refreshToken)
        }

        data class Delete(
            val id: String
        )

        data class Update(
            val providerId: String,
            val name: String,
            val accessToken: String,
            val refreshToken: String
        )

        data class Response(
            val id: String,
            val name: String
        )
    }
}