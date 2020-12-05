package com.donghyukki.korello.board.service

import com.donghyukki.korello.KorelloApplicationTests
import com.donghyukki.korello.board.dto.BoardDTO
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

internal class BoardServiceTest(
    @Autowired val boardService: BoardService
): KorelloApplicationTests() {

    @Test
    fun getAllBoards() {
        boardService.createBoard(boardCreateDTO = BoardDTO.Companion.Create("Test"))
        assertThat(boardService.getAllBoards()).isNotEmpty
    }

    @Test
    fun createBoard() {
        val board = boardService.createBoard(boardCreateDTO = BoardDTO.Companion.Create("Test"))
        assertThat(board.name).isEqualTo("Test")
    }

}