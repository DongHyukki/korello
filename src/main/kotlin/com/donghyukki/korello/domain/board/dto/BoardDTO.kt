package com.donghyukki.korello.domain.board.dto

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
        data class JoinMember(
            val memberId: String,
            val boardId: String
        )
        data class ExitMember(
            val memberId: String,
            val boardId: String
        )
    }
}