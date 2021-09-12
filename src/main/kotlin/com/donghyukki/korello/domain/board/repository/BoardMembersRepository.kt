package com.donghyukki.korello.domain.board.repository

import com.donghyukki.korello.domain.board.model.BoardJoinMembers
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface BoardMembersRepository {
    fun save(boardJoinMembers: BoardJoinMembers): BoardJoinMembers
    fun delete(boardJoinMembers: BoardJoinMembers)
}
