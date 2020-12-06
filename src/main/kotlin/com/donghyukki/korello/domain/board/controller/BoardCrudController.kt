package com.donghyukki.korello.domain.board.controller

import com.donghyukki.korello.domain.board.dto.BoardDTO.Companion.Create
import com.donghyukki.korello.domain.board.dto.BoardDTO.Companion.Delete
import com.donghyukki.korello.domain.board.service.BoardCrudService
import com.donghyukki.korello.core.dto.response.KorelloResponse
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class BoardCrudController(
    private val boardCrudService: BoardCrudService
) {

    @Operation(summary = "BOARD 조회", description = "모든 BOARD를 조회 한다")
    @GetMapping("api/v1/boards")
    fun getBoards(): KorelloResponse {
        return KorelloResponse(boardCrudService.getAllBoards())
    }

    @Operation(summary = "BOARD 조회", description = "특정 BOARD를 조회 한다")
    @GetMapping("api/v1/board/{id}")
    fun getBoard(@PathVariable id: String): KorelloResponse {
        return KorelloResponse(boardCrudService.getBoard(id.toLong()))
    }

    @Operation(summary = "BOARD 생성", description = "BOARD를 생성 한다.")
    @PostMapping("api/v1/board")
    fun createBoard(@RequestBody boardCreateDTO: Create): KorelloResponse {
        boardCrudService.createBoard(boardCreateDTO)
        return KorelloResponse(HttpStatus.CREATED)
    }

    @Operation(summary = "BOARD 수정", description = "BOARD를 수정 한다.")
    @PutMapping("api/v1/board")
    fun updateBoard(): KorelloResponse {
        return KorelloResponse(HttpStatus.NOT_IMPLEMENTED,"미구현")
    }

    @Operation(summary = "BOARD 삭제", description = "BOARD를 삭제 한다.")
    @PostMapping("api/v1/board/delete")
    fun deleteBoard(@RequestBody boardDeleteDTO: Delete): KorelloResponse {
        boardCrudService.deleteBoard(boardDeleteDTO)
        return KorelloResponse()
    }
}