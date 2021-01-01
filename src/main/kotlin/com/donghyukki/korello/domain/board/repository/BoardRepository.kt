package com.donghyukki.korello.domain.board.repository

import com.donghyukki.korello.domain.board.model.Board
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardRepository: JpaRepository<Board, Long> {

}