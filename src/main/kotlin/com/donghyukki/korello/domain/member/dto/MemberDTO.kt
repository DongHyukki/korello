package com.donghyukki.korello.domain.member.dto

import com.donghyukki.korello.domain.board.model.Board
import com.donghyukki.korello.domain.member.model.Member

class MemberDTO {

    companion object {
        data class Create(
            val name: String
        ) {
            fun toEntity() = Member(name)
        }
    }
}