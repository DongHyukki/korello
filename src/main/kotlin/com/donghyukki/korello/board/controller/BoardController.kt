package com.donghyukki.korello.board.controller

import com.donghyukki.korello.board.domain.Board
import com.donghyukki.korello.board.dto.BoardDTO.Companion.Create
import com.donghyukki.korello.board.service.BoardService
import com.donghyukki.korello.core.dto.response.KorelloResponse
import com.donghyukki.korello.core.exception.KorelloException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.lang.RuntimeException

@RestController
class BoardController(
    val boardService: BoardService
) {

    @GetMapping("api/v1/boards")
    fun getBoards(): KorelloResponse {
        return KorelloResponse(boardService.getAllBoards())
    }

    @GetMapping("api/v1/board/{id}")
    fun getBoard(@PathVariable id: String) {
    }

    @PostMapping("api/v1/board")
    fun createBoard(@RequestBody boardCreateDTO: Create): KorelloResponse {
        boardService.createBoard(boardCreateDTO)
        return KorelloResponse(HttpStatus.CREATED)
    }

    @PutMapping("api/v1/board")
    fun updateBoard() {
    }

    @DeleteMapping("api/v1/board")
    fun deleteBoard() {

    }


}