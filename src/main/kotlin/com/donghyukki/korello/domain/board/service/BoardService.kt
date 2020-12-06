package com.donghyukki.korello.domain.board.service

import com.donghyukki.korello.domain.board.dto.BoardDTO.Companion.ExitMember
import com.donghyukki.korello.domain.board.dto.BoardDTO.Companion.JoinMember
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
    fun inviteMember(joinMember: JoinMember): BoardMembers {
        val member = memberCrudService.getMember(joinMember.memberId.toLong())
        val board = boardCrudService.getBoard(joinMember.boardId.toLong())
        return boardMembersService.joinBoard(member, board)
    }

    @Transactional
    fun exitJoinMember(ExitMember: ExitMember) {
        val board = boardCrudService.getBoard(ExitMember.boardId.toLong())
        val member = memberCrudService.getMember(ExitMember.memberId.toLong())
        val joinBoardMembers = member.boards.first { boardMembers -> boardMembers.board == board }
        boardMembersService.exitBoard(joinBoardMembers)
        board.deleteMember(joinBoardMembers)
        member.exitBoard(joinBoardMembers)
    }
}