package com.donghyukki.korello.application.services.board

import com.donghyukki.korello.application.port.AuthenticationFacade
import com.donghyukki.korello.application.services.member.MemberCrudService
import com.donghyukki.korello.domain.board.model.BoardJoinMembers
import com.donghyukki.korello.domain.board.service.BoardJoinMembersService
import com.donghyukki.korello.infrastructure.web.event.publisher.KorelloEventPublisher
import com.donghyukki.korello.presentation.dto.BoardDTO.Companion.MemberExit
import com.donghyukki.korello.presentation.dto.BoardDTO.Companion.MemberJoin
import com.donghyukki.korello.presentation.dto.BoardDTO.Companion.MemberResponse
import com.donghyukki.korello.presentation.dto.BoardDTO.Companion.Response
import com.donghyukki.korello.presentation.dto.EventDTO
import com.donghyukki.korello.presentation.dto.type.KorelloActionType
import com.donghyukki.korello.presentation.dto.type.KorelloEventType
import com.donghyukki.korello.presentation.dto.type.KorelloSelectType
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardMemberService(
    private val boardCrudService: BoardCrudService,
    private val boardJoinMembersService: BoardJoinMembersService,
    private val memberCrudService: MemberCrudService,
    private val korelloEventPublisher: KorelloEventPublisher,
    private val authenticationFacade: AuthenticationFacade,
) {

    @Transactional(readOnly = true)
    fun getJoinBoards(): List<Response> {
        val memberId = authenticationFacade.getMemberId()
        val member = memberCrudService.getMemberEntity(memberId)
        return member.getJoinBoards()
    }

    @Cacheable(value = ["members"], key = "#boardId")
    @Transactional(readOnly = true)
    fun getJoinMembers(boardId: String): List<MemberResponse> {
        return boardCrudService.getBoardEntity(boardId.toLong()).members.map { boardMembers ->
            MemberResponse(boardMembers.member.id.toString(), boardMembers.member.name)
        }.toList()
    }

    @CacheEvict(value = ["members"], key = "#memberJoinDTO.boardId")
    @Transactional
    fun inviteMember(memberJoinDTO: MemberJoin): BoardJoinMembers {
        val member = memberCrudService.getMemberEntity(memberJoinDTO.memberId.toLong())
        val board = boardCrudService.getBoardEntity(memberJoinDTO.boardId.toLong())
        val joinBoard = boardJoinMembersService.joinBoard(member, board)
        korelloEventPublisher.publishEvent(EventDTO(board.id!!, KorelloSelectType.BOARD, KorelloEventType.MEMBER, KorelloActionType.INVITE))
        return joinBoard
    }

    @CacheEvict(value = ["members"], key = "#memberExitDTO.boardId")
    @Transactional
    fun exitJoinMember(memberExitDTO: MemberExit) {
        val board = boardCrudService.getBoardEntity(memberExitDTO.boardId.toLong())
        val member = memberCrudService.getMemberEntity(memberExitDTO.memberId.toLong())
        val joinBoardMembers = member.boardJoins.first { boardMembers -> boardMembers.board == board }
        boardJoinMembersService.exitBoard(joinBoardMembers)
        board.deleteMember(joinBoardMembers)
        member.exitBoard(joinBoardMembers)
        korelloEventPublisher.publishEvent(EventDTO(board.id!!, KorelloSelectType.BOARD, KorelloEventType.MEMBER, KorelloActionType.EXIT))
    }
}
