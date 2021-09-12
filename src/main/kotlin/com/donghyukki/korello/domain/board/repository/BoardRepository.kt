package com.donghyukki.korello.domain.board.repository

import com.donghyukki.korello.domain.board.model.Board
import java.util.Optional

interface BoardRepository {
    fun save(board: Board): Board
    fun findById(id: Long): Optional<Board>
    fun findAll(): List<Board>
    fun deleteById(id: Long)
}
