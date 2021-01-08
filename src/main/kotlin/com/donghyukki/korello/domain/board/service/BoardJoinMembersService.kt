package com.donghyukki.korello.domain.board.service

import com.donghyukki.korello.domain.board.model.Board
import com.donghyukki.korello.domain.board.model.BoardJoinMembers
import com.donghyukki.korello.domain.board.repository.BoardMembersRepository
import com.donghyukki.korello.domain.member.model.Member
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardJoinMembersService(
    val boardMembersRepository: BoardMembersRepository
) {
    @Transactional
    fun joinBoard(member: Member, board: Board): BoardJoinMembers {
        val boardMembers = BoardJoinMembers(board, member)
        val savedMembers = boardMembersRepository.save(boardMembers)
        savedMembers.joinBoard()
        return savedMembers
    }

    @Transactional
    fun exitBoard(boardJoinMembers: BoardJoinMembers) {
        boardMembersRepository.delete(boardJoinMembers)

    }
}