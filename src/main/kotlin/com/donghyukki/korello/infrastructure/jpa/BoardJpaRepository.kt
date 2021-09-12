package com.donghyukki.korello.infrastructure.jpa

import com.donghyukki.korello.domain.board.model.Board
import com.donghyukki.korello.domain.board.repository.BoardRepository
import org.springframework.data.jpa.repository.JpaRepository

interface BoardJpaRepository : BoardRepository, JpaRepository<Board, Long>
