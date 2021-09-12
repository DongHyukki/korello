package com.donghyukki.korello.domain.todo.repository

import com.donghyukki.korello.domain.todo.model.Todo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.Optional
import javax.swing.text.html.Option

interface TodoRepository {
    fun save(todo: Todo): Todo
    fun findById(id: Long): Optional<Todo>
    fun deleteById(id: Long)
    fun getTodosByCardId(cardId: Long): List<Todo>
}
