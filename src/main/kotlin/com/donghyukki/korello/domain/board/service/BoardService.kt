package com.donghyukki.korello.domain.board.service

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
    fun inviteMember(boardId: Long, memberId: Long): BoardMembers {
        val member = memberCrudService.getMember(memberId)
        val board = boardCrudService.getBoard(boardId)
        return boardMembersService.joinBoard(member, board)
    }

    @Transactional
    fun exitJoinMember(boardId: Long, memberId: Long) {
        val board = boardCrudService.getBoard(boardId)
        val member = memberCrudService.getMember(memberId)
        val joinBoardMembers = member.boards.first { boardMembers -> boardMembers.board == board }
        boardMembersService.exitBoard(joinBoardMembers)
        board.deleteMember(joinBoardMembers)
        member.exitBoard(joinBoardMembers)
    }
}