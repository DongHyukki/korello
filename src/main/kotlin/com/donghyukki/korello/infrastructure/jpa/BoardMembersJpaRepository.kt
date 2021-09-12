package com.donghyukki.korello.infrastructure.jpa

import com.donghyukki.korello.domain.board.model.BoardJoinMembers
import com.donghyukki.korello.domain.board.repository.BoardMembersRepository
import org.springframework.data.jpa.repository.JpaRepository

interface BoardMembersJpaRepository : BoardMembersRepository, JpaRepository<BoardJoinMembers, Long>
