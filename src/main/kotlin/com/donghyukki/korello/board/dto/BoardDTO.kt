package com.donghyukki.korello.board.dto

import com.donghyukki.korello.board.domain.Board

class BoardDTO {

    companion object {
        data class Create(
            val name: String
        ) {
            fun toEntity() = Board(null, name, null)
        }
    }
}