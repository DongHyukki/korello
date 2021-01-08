package com.donghyukki.korello.domain.label.repository

import com.donghyukki.korello.domain.label.model.Label
import com.donghyukki.korello.domain.todo.model.Todo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface LabelRepository : JpaRepository<Label, Long> {

    @Query("select l from Label l join fetch l.board where l.board.id=:boardId")
    fun getLabelsByBoardId(@Param("boardId") boardId: Long): List<Label>
}
