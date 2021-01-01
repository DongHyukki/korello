package com.donghyukki.korello.domain.board.service

import com.donghyukki.korello.presentation.dto.BoardDTO.Companion.Create
import com.donghyukki.korello.presentation.dto.BoardDTO.Companion.Delete
import com.donghyukki.korello.domain.board.model.Board
import com.donghyukki.korello.domain.board.repository.BoardRepository
import com.donghyukki.korello.presentation.dto.BoardDTO
import com.donghyukki.korello.presentation.dto.BoardDTO.Companion.Response
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardCrudService(
    val boardRepository: BoardRepository
) {
    @Transactional(readOnly = true)
    fun getAllBoards(): List<Response> {
        return boardRepository.findAll().map { board ->
            Response(
                board.id.toString()
                , board.name
                , board.members.map { boardJoinMembers -> boardJoinMembers.member.name }.toList()
                , board.createDate
                , board.updateDate
            )}.toList()
    }

    @Transactional(readOnly = true)
    fun getBoard(id: Long): Response {
        val board = boardRepository.findById(id).orElseThrow { IllegalArgumentException("Board Not Existed") }
        return Response(board.id.toString()
            , board.name
            , board.members.map { boardJoinMembers -> boardJoinMembers.member.name }.toList()
            , board.createDate
            , board.updateDate
        )
    }

    @Transactional(readOnly = true)
    fun getBoardEntity(id: Long): Board {
        return boardRepository.findById(id).orElseThrow { IllegalArgumentException("Board Not Existed") }
    }

    @Transactional
    fun createBoard(boardCreateDTO: Create): Board {
        return boardRepository.save(boardCreateDTO.toEntity())
    }

    @Transactional
    fun deleteBoard(boardDeleteDTO: Delete) {
        val board = boardRepository.findById(boardDeleteDTO.id.toLong()).orElseThrow()
        board.clearCard()
        return boardRepository.deleteById(boardDeleteDTO.id.toLong())
    }

}