package com.donghyukki.korello.presentation.dto

import com.donghyukki.korello.domain.board.model.Board

class BoardDTO {

    companion object {
        data class Create(
            val name: String
        ) {
            fun toEntity() = Board(name)
        }
        data class Delete(
            val id: String
        )
        data class MemberJoin(
            val memberId: String,
            val boardId: String
        )
        data class MemberExit(
            val memberId: String,
            val boardId: String
        )
        data class MemberResponse(
            val memberId: String,
            val memberName: String
        )
    }
}