package com.donghyukki.korello.presentation.dto

import com.donghyukki.korello.domain.board.model.Board
import com.donghyukki.korello.domain.member.model.Member

class MemberDTO {

    companion object {
        data class Create(
            val name: String
        ) {
            fun toEntity() = Member(name)
        }
        data class Delete(
            val id: String
        )
    }
}