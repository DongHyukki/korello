package com.donghyukki.korello.domain.board.service

import com.donghyukki.korello.domain.board.model.Board
import com.donghyukki.korello.domain.board.model.BoardJoinMembers
import com.donghyukki.korello.domain.board.repository.BoardMembersRepository
import com.donghyukki.korello.domain.member.model.Member
import com.donghyukki.korello.presentation.dto.EventDTO
import com.donghyukki.korello.presentation.dto.type.KorelloActionType
import com.donghyukki.korello.presentation.dto.type.KorelloEventType
import com.donghyukki.korello.presentation.dto.type.KorelloSelectType
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardJoinMembersService(
    private val boardMembersRepository: BoardMembersRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) {
    @Transactional
    fun joinBoard(member: Member, board: Board): BoardJoinMembers {
        val boardMembers = BoardJoinMembers(board, member)
        val savedMembers = boardMembersRepository.save(boardMembers)
        savedMembers.joinBoard()
        applicationEventPublisher.publishEvent(EventDTO(board.id!!, KorelloSelectType.BOARD, KorelloEventType.MEMBER, KorelloActionType.JOIN))
        return savedMembers
    }

    @Transactional
    fun exitBoard(boardJoinMembers: BoardJoinMembers) {
        boardMembersRepository.delete(boardJoinMembers)
        applicationEventPublisher.publishEvent(EventDTO(boardJoinMembers.board.id!!, KorelloSelectType.BOARD, KorelloEventType.MEMBER, KorelloActionType.EXIT))
    }
}