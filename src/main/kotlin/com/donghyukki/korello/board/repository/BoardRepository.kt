package com.donghyukki.korello.board.repository

import com.donghyukki.korello.board.domain.Board
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardRepository: JpaRepository<Board, Long>