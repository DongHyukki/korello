package com.donghyukki.korello.application.services

import com.donghyukki.korello.domain.board.model.BoardJoinMembers
import com.donghyukki.korello.domain.board.service.BoardCrudService
import com.donghyukki.korello.domain.board.service.BoardJoinMembersService
import com.donghyukki.korello.domain.member.service.MemberCrudService
import com.donghyukki.korello.presentation.dto.BoardDTO.Companion.MemberBoards
import com.donghyukki.korello.presentation.dto.BoardDTO.Companion.MemberExit
import com.donghyukki.korello.presentation.dto.BoardDTO.Companion.MemberJoin
import com.donghyukki.korello.presentation.dto.BoardDTO.Companion.MemberResponse
import com.donghyukki.korello.presentation.dto.BoardDTO.Companion.Response
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardMemberService(
    val boardCrudService: BoardCrudService,
    val boardJoinMembersService: BoardJoinMembersService,
    val memberCrudService: MemberCrudService,
) {

    @Transactional(readOnly = true)
    fun getJoinBoards(memberBoardsDTO: MemberBoards): List<Response> {
        val member = memberCrudService.getMemberEntity(memberBoardsDTO.memberId.toLong())
        return member.getJoinBoards()
    }

    @Transactional(readOnly = true)
    fun getJoinMembers(boardId: String): List<MemberResponse> {
        return boardCrudService.getBoardEntity(boardId.toLong()).members.map {
                boardMembers -> MemberResponse(boardMembers.member.id.toString(), boardMembers.member.name)
        }.toList()
    }

    @Transactional
    fun inviteMember(memberJoinDTO: MemberJoin): BoardJoinMembers {
        val member = memberCrudService.getMemberEntity(memberJoinDTO.memberId.toLong())
        val board = boardCrudService.getBoardEntity(memberJoinDTO.boardId.toLong())
        return boardJoinMembersService.joinBoard(member, board)
    }

    @Transactional
    fun exitJoinMember(memberExitDTO: MemberExit) {
        val board = boardCrudService.getBoardEntity(memberExitDTO.boardId.toLong())
        val member = memberCrudService.getMemberEntity(memberExitDTO.memberId.toLong())
        val joinBoardMembers = member.boardJoins.first { boardMembers -> boardMembers.board == board }
        boardJoinMembersService.exitBoard(joinBoardMembers)
        board.deleteMember(joinBoardMembers)
        member.exitBoard(joinBoardMembers)
    }
}