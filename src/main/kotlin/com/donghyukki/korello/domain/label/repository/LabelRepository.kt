package com.donghyukki.korello.domain.label.repository

import com.donghyukki.korello.domain.label.model.Label
import com.donghyukki.korello.domain.todo.model.Todo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.Optional

interface LabelRepository {
    fun save(label: Label): Label
    fun findById(id: Long): Optional<Label>
    fun deleteById(id: Long)
    fun findAllById(ids: Iterable<Long>): List<Label>
    fun getLabelsByBoardId(boardId: Long): List<Label>
}
