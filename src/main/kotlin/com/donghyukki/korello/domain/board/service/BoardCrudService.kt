package com.donghyukki.korello.domain.board.service

import com.donghyukki.korello.domain.board.dto.BoardDTO.Companion.Create
import com.donghyukki.korello.domain.board.dto.BoardDTO.Companion.Delete
import com.donghyukki.korello.domain.board.model.Board
import com.donghyukki.korello.domain.board.repository.BoardRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardCrudService(
    val boardRepository: BoardRepository
) {
    @Transactional(readOnly = true)
    fun getAllBoards(): MutableList<Board> {
        return boardRepository.findAll()
    }

    @Transactional(readOnly = true)
    fun getBoard(id: Long): Board {
        return boardRepository.findById(id).orElseThrow { IllegalArgumentException("Board Not Existed") }
    }

    @Transactional
    fun createBoard(boardCreateDTO: Create): Board {
        return boardRepository.save(boardCreateDTO.toEntity())
    }

    @Transactional
    fun deleteBoard(boardDeleteDTO: Delete) {
        return boardRepository.deleteById(boardDeleteDTO.id.toLong())
    }

}