package com.donghyukki.korello.domain.board.service

import com.donghyukki.korello.domain.board.model.Board
import com.donghyukki.korello.domain.board.repository.BoardRepository
import com.donghyukki.korello.infrastructure.exception.KorelloNotFoundException
import com.donghyukki.korello.presentation.dto.BoardDTO.Companion.Create
import com.donghyukki.korello.presentation.dto.BoardDTO.Companion.Delete
import com.donghyukki.korello.presentation.dto.BoardDTO.Companion.Response
import com.donghyukki.korello.presentation.dto.EventDTO
import com.donghyukki.korello.presentation.dto.type.KorelloActionType
import com.donghyukki.korello.presentation.dto.type.KorelloEventType
import com.donghyukki.korello.presentation.dto.type.KorelloSelectType
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.annotation.PostConstruct

@Service
class BoardCrudService(
    private val boardRepository: BoardRepository,
    private val boardJoinMembersService: BoardJoinMembersService,
    private val applicationEventPublisher: ApplicationEventPublisher,

) {
    @PostConstruct
    fun setUp() {
        println("BoardCrudService Constructed")
    }

    init {
        println("BoardCrudService Initialized")
    }

    @Transactional(readOnly = true)
    fun getAllBoards(): List<Response> {
        return boardRepository.findAll().map { board ->
            Response(
                board.id.toString(),
                board.name,
                board.members.map { boardJoinMembers -> boardJoinMembers.member.name }.toList(),
                board.createDate,
                board.updateDate
            )
        }.toList()
    }

    @Transactional(readOnly = true)
    fun getBoard(id: Long): Response {
        val board = boardRepository.findById(id).orElseThrow { KorelloNotFoundException() }
        return Response(
            board.id.toString(),
            board.name,
            board.members.map { boardJoinMembers -> boardJoinMembers.member.name }.toList(),
            board.createDate,
            board.updateDate
        )
    }

    @Transactional(readOnly = true)
    fun getBoardEntity(id: Long): Board {
        return boardRepository.findById(id).orElseThrow { KorelloNotFoundException() }
    }

    @Transactional
    fun createBoard(boardCreateDTO: Create): Response {
        val board = boardRepository.save(boardCreateDTO.toEntity())
        boardJoinMembersService.selfJoinBoard(board)
        applicationEventPublisher.publishEvent(EventDTO(board.id!!, KorelloSelectType.BOARD, KorelloEventType.BOARD, KorelloActionType.CREATE))
        return Response(
            board.id.toString(),
            board.name,
            board.members.map { boardJoinMembers -> boardJoinMembers.member.name }.toList(),
            board.createDate,
            board.updateDate
        )
    }

    @Transactional
    fun deleteBoard(boardDeleteDTO: Delete) {
        val board = boardRepository.findById(boardDeleteDTO.id.toLong()).orElseThrow { KorelloNotFoundException() }
        board.clearCard()
        applicationEventPublisher.publishEvent(EventDTO(board.id!!, KorelloSelectType.BOARD, KorelloEventType.BOARD, KorelloActionType.DELETE))
        return boardRepository.deleteById(boardDeleteDTO.id.toLong())
    }

}