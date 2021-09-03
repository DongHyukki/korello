//package com.donghyukki.korello.board.service
//
//import com.donghyukki.korello.KorelloApplicationTests
//import com.donghyukki.korello.application.services.board.BoardCrudService
//import com.donghyukki.korello.presentation.dto.BoardDTO
//import org.assertj.core.api.Assertions.assertThat
//import org.assertj.core.api.Assertions.assertThatExceptionOfType
//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//
//internal class BoardMemberServiceTest(
//    @Autowired val boardCrudService: BoardCrudService
//) : KorelloApplicationTests() {
//
//    @Test
//    fun getAllBoards() {
//        boardCrudService.createBoard(boardCreateDTO = BoardDTO.Companion.Create("Test"))
//        assertThat(boardCrudService.getAllBoards()).isNotEmpty
//    }
//
//    @Test
//    fun createBoard() {
//        val board = boardCrudService.createBoard(boardCreateDTO = BoardDTO.Companion.Create("Test"))
//        assertThat(board.name).isEqualTo("Test")
//        assertThatExceptionOfType(IllegalArgumentException::class.java)
//            .isThrownBy { throw IllegalStateException() }
//    }
//
//    @Test
//    fun getBoard() {
//        boardCrudService.getBoard(1132L)
//    }
//
//
//}
