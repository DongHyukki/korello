package com.donghyukki.korello.domain.board.service

import com.donghyukki.korello.domain.board.dto.BoardDTO
import com.donghyukki.korello.domain.board.dto.BoardDTO.Companion.MemberExit
import com.donghyukki.korello.domain.board.dto.BoardDTO.Companion.MemberJoin
import com.donghyukki.korello.domain.board.dto.BoardDTO.Companion.MemberResponse
import com.donghyukki.korello.domain.board.model.BoardMembers
import com.donghyukki.korello.domain.member.service.MemberCrudService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardService(
    val boardCrudService: BoardCrudService,
    val boardMembersService: BoardMembersService,
    val memberCrudService: MemberCrudService,
) {

    @Transactional
    fun getJoinMembers(boardId: String): List<MemberResponse> {
        return boardCrudService.getBoard(boardId.toLong()).members.map {
                boardMembers -> MemberResponse(boardMembers.member.id.toString(), boardMembers.member.name)
        }.toList()
    }

    @Transactional
    fun inviteMember(memberJoin: MemberJoin): BoardMembers {
        val member = memberCrudService.getMember(memberJoin.memberId.toLong())
        val board = boardCrudService.getBoard(memberJoin.boardId.toLong())
        return boardMembersService.joinBoard(member, board)
    }

    @Transactional
    fun exitJoinMember(MemberExit: MemberExit) {
        val board = boardCrudService.getBoard(MemberExit.boardId.toLong())
        val member = memberCrudService.getMember(MemberExit.memberId.toLong())
        val joinBoardMembers = member.boards.first { boardMembers -> boardMembers.board == board }
        boardMembersService.exitBoard(joinBoardMembers)
        board.deleteMember(joinBoardMembers)
        member.exitBoard(joinBoardMembers)
    }
}