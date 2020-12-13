package com.donghyukki.korello.domain.board.controller

import com.donghyukki.korello.core.dto.response.KorelloResponse
import com.donghyukki.korello.domain.board.dto.BoardDTO.Companion.MemberJoin
import com.donghyukki.korello.domain.board.dto.BoardDTO.Companion.MemberExit
import com.donghyukki.korello.domain.board.service.BoardService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class BoardMemberController(
    private val boardService: BoardService
) {

    @Operation(summary = "BOARD 멤버 조회", description = "특정 BOARD에 속한 멤버들을 조회한다.")
    @GetMapping("api/v1/board/{id}/members")
    private fun getJoinMember(@PathVariable id: String): KorelloResponse {
        return KorelloResponse(boardService.getJoinMembers(id))
    }

    @Operation(summary = "BOARD 멤버 참여", description = "특정 BOARD에 멤버를 참여 시킨다.")
    @PostMapping("api/v1/board/{id}/member/join")
    fun joinMemberToBoard(@PathVariable id: String, @RequestBody memberJoinDTO: MemberJoin): KorelloResponse {
        boardService.inviteMember(memberJoinDTO)
        return KorelloResponse(HttpStatus.OK)
    }

    @Operation(summary = "BOARD 멤버 제외", description = "특정 BOARD에 멤버를 제외 시킨다.")
    @PostMapping("api/v1/board/{id}/member/exit")
    fun exitMemberFromBoard(@PathVariable id: String, @RequestBody memberExitDTO: MemberExit): KorelloResponse {
        boardService.exitJoinMember(memberExitDTO)
        return KorelloResponse(HttpStatus.OK)
    }
}