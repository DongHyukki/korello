package com.donghyukki.korello.domain.board.controller

import com.donghyukki.korello.core.dto.response.KorelloResponse
import com.donghyukki.korello.domain.board.dto.BoardDTO.Companion.JoinMember
import com.donghyukki.korello.domain.board.dto.BoardDTO.Companion.ExitMember
import com.donghyukki.korello.domain.board.service.BoardService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class BoardController(
    private val boardService: BoardService
) {

    @Operation(summary = "BOARD 멤버 참여", description = "특정 BOARD에 멤버를 참여 시킨다.")
    @PostMapping("api/v1/board/{id}/member/join")
    fun memberJoinToBoard(@PathVariable id: String, @RequestBody joinMemberDTO: JoinMember): KorelloResponse {
        boardService.inviteMember(joinMemberDTO)
        return KorelloResponse(HttpStatus.OK)
    }

    @Operation(summary = "BOARD 멤버 제외", description = "특정 BOARD에 멤버를 제외 시킨다.")
    @PostMapping("api/v1/board/{id}/member/exit")
    fun memberExitFromBoard(@PathVariable id: String, @RequestBody exitMemberDTO: ExitMember): KorelloResponse {
        boardService.exitJoinMember(exitMemberDTO)
        return KorelloResponse(HttpStatus.OK)
    }
}