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
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardCrudService(
    private val boardRepository: BoardRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) {
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
    fun createBoard(boardCreateDTO: Create): Board {
        val board = boardRepository.save(boardCreateDTO.toEntity())
//        applicationEventPublisher.publishEvent(EventDTO(board.id!!, KorelloEventType.BOARD.toAddedPostName(), KorelloActionType.CREATE.kor_name))
        applicationEventPublisher.publishEvent(EventDTO(board.id!!, KorelloEventType.BOARD, KorelloActionType.CREATE))
        return board
    }

    @Transactional
    fun deleteBoard(boardDeleteDTO: Delete) {
        val board = boardRepository.findById(boardDeleteDTO.id.toLong()).orElseThrow { KorelloNotFoundException() }
        board.clearCard()
        return boardRepository.deleteById(boardDeleteDTO.id.toLong())
    }

}