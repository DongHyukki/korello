package com.donghyukki.korello.infrastructure.jpa

import com.donghyukki.korello.domain.label.model.Label
import com.donghyukki.korello.domain.label.repository.LabelRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface LabelJpaRepository : LabelRepository, JpaRepository<Label, Long> {
    @Query("select l from Label l join fetch l.board where l.board.id=:boardId")
    override fun getLabelsByBoardId(@Param("boardId") boardId: Long): List<Label>
}
