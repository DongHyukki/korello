package com.donghyukki.korello.infrastructure.jpa

import com.donghyukki.korello.domain.todo.model.Todo
import com.donghyukki.korello.domain.todo.repository.TodoRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface TodoJpaRepository : TodoRepository, JpaRepository<Todo, Long> {
    @Query("select t from Todo t join fetch t.card where t.card.id=:cardId")
    override fun getTodosByCardId(@Param("cardId") cardId: Long): List<Todo>
}
