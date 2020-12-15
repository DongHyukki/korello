package com.donghyukki.korello.application.services

import com.donghyukki.korello.presentation.dto.BoardDTO.Companion.MemberExit
import com.donghyukki.korello.presentation.dto.BoardDTO.Companion.MemberJoin
import com.donghyukki.korello.presentation.dto.BoardDTO.Companion.MemberResponse
import com.donghyukki.korello.domain.board.model.BoardJoinMembers
import com.donghyukki.korello.domain.board.service.BoardCrudService
import com.donghyukki.korello.domain.board.service.BoardJoinMembersService
import com.donghyukki.korello.domain.member.service.MemberCrudService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardMemberService(
    val boardCrudService: BoardCrudService,
    val boardJoinMembersService: BoardJoinMembersService,
    val memberCrudService: MemberCrudService,
) {

    @Transactional
    fun getJoinMembers(boardId: String): List<MemberResponse> {
        return boardCrudService.getBoard(boardId.toLong()).members.map {
                boardMembers -> MemberResponse(boardMembers.member.id.toString(), boardMembers.member.name)
        }.toList()
    }

    @Transactional
    fun inviteMember(memberJoin: MemberJoin): BoardJoinMembers {
        val member = memberCrudService.getMember(memberJoin.memberId.toLong())
        val board = boardCrudService.getBoard(memberJoin.boardId.toLong())
        return boardJoinMembersService.joinBoard(member, board)
    }

    @Transactional
    fun exitJoinMember(MemberExit: MemberExit) {
        val board = boardCrudService.getBoard(MemberExit.boardId.toLong())
        val member = memberCrudService.getMember(MemberExit.memberId.toLong())
        val joinBoardMembers = member.boardJoins.first { boardMembers -> boardMembers.board == board }
        boardJoinMembersService.exitBoard(joinBoardMembers)
        board.deleteMember(joinBoardMembers)
        member.exitBoard(joinBoardMembers)
    }
}