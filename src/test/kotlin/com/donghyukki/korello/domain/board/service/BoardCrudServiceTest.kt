package com.donghyukki.korello.domain.board.service

import com.donghyukki.korello.KorelloApplicationTests
import com.donghyukki.korello.application.services.board.BoardCrudService
import com.donghyukki.korello.domain.board.model.Board
import com.donghyukki.korello.domain.board.repository.BoardRepository
import com.donghyukki.korello.presentation.dto.BoardDTO
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

internal class BoardCrudServiceTest(
    @Autowired val boardRepository: BoardRepository
) : KorelloApplicationTests() {

//    @Test
//    @Transactional
//    fun getAllBoards() {
//        boardCrudService.createBoard(boardCreateDTO = BoardDTO.Companion.Create("Test"))
//        assertThat(boardCrudService.getAllBoards()).isNotEmpty
//    }

    @Test
    @Transactional
    fun createBoard() {
//        val board = boardCrudService.createBoard(boardCreateDTO = BoardDTO.Companion.Create("Test"))
//        assertThat(board.name).isEqualTo("Test")
        val save = boardRepository.save(Board("테스트보드"))
        assertThat(save.id).isNotNull
    }

}
