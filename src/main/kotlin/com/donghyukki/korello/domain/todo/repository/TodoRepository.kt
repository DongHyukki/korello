package com.donghyukki.korello.domain.todo.repository

import com.donghyukki.korello.domain.todo.model.Todo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface TodoRepository: JpaRepository<Todo, Long> {

    @Query("select t from Todo t join fetch t.card where t.card.id=:cardId")
    fun getTodosByCardId(@Param("cardId") cardId: Long): List<Todo>

}