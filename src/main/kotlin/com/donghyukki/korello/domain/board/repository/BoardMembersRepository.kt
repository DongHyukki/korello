package com.donghyukki.korello.domain.board.repository

import com.donghyukki.korello.domain.board.model.BoardMembers
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface BoardMembersRepository: JpaRepository<BoardMembers, Long>