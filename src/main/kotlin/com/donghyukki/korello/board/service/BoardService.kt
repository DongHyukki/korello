package com.donghyukki.korello.board.service

import com.donghyukki.korello.board.domain.Board
import com.donghyukki.korello.board.dto.BoardDTO
import com.donghyukki.korello.board.repository.BoardRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class BoardService(
    val boardRepository: BoardRepository
) {
    @Transactional(readOnly = true)
    fun getAllBoards(): MutableList<Board> {
        return boardRepository.findAll()
    }

    @Transactional(readOnly = true)
    fun getBoard(id: Long): Optional<Board> {
        return boardRepository.findById(id)
    }

    @Transactional
    fun createBoard(boardCreateDTO: BoardDTO.Companion.Create): Board {
        return boardRepository.save(boardCreateDTO.toEntity())
    }
}