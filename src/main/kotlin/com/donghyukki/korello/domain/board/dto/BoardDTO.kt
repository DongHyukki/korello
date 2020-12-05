package com.donghyukki.korello.domain.board.dto

import com.donghyukki.korello.domain.board.model.Board

class BoardDTO {

    companion object {
        data class Create(
            val name: String
        ) {
            fun toEntity() = Board(name)
        }
    }
}