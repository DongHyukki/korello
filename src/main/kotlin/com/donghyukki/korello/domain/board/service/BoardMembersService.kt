package com.donghyukki.korello.domain.board.service

import com.donghyukki.korello.domain.board.dto.BoardDTO
import com.donghyukki.korello.domain.board.model.Board
import com.donghyukki.korello.domain.board.model.BoardMembers
import com.donghyukki.korello.domain.board.repository.BoardMembersRepository
import com.donghyukki.korello.domain.board.repository.BoardRepository
import com.donghyukki.korello.domain.member.model.Member
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class BoardMembersService(
    val boardMembersRepository: BoardMembersRepository
) {
    @Transactional
    fun joinBoard(member: Member, board: Board): BoardMembers {
        val boardMembers = BoardMembers(board, member)
        val savedMembers = boardMembersRepository.save(boardMembers)
        savedMembers.joinBoard()
        return savedMembers
    }

    @Transactional
    fun exitBoard(boardMembers: BoardMembers) {
        boardMembersRepository.delete(boardMembers)
    }
}