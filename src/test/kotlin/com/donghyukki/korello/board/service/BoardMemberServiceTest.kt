package com.donghyukki.korello.board.service

import com.donghyukki.korello.KorelloApplicationTests
import com.donghyukki.korello.presentation.dto.BoardDTO
import com.donghyukki.korello.domain.board.service.BoardCrudService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class BoardMemberServiceTest(
    @Autowired val boardCrudService: BoardCrudService
): KorelloApplicationTests() {

    @Test
    fun getAllBoards() {
        boardCrudService.createBoard(boardCreateDTO = BoardDTO.Companion.Create("Test"))
        assertThat(boardCrudService.getAllBoards()).isNotEmpty
    }

    @Test
    fun createBoard() {
        val board = boardCrudService.createBoard(boardCreateDTO = BoardDTO.Companion.Create("Test"))
        assertThat(board.name).isEqualTo("Test")
    }

}