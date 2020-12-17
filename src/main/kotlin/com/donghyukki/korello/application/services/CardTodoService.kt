package com.donghyukki.korello.application.services

import com.donghyukki.korello.domain.card.repository.CardRepository
import com.donghyukki.korello.domain.todo.model.Todo
import com.donghyukki.korello.domain.todo.repository.TodoRepository
import com.donghyukki.korello.presentation.dto.TodoDTO
import com.donghyukki.korello.presentation.dto.TodoDTO.Companion.Create
import com.donghyukki.korello.presentation.dto.TodoDTO.Companion.Response
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CardTodoService(
    private val cardRepository: CardRepository,
    private val todoRepository: TodoRepository
) {

    @Transactional(readOnly = true)
    fun getTodosByCardId(cardId: String): List<Response> {
        return todoRepository.getTodosByCardId(cardId.toLong()).map { todo -> Response(todo.id.toString(), todo.title, todo.status) }.toList()
    }

    @Transactional
    fun createTodo(cardId: String, todoCreateDTO: Create): Todo {
        val card = cardRepository.findById(cardId.toLong()).orElseThrow()
        return todoRepository.save(Todo(todoCreateDTO.title, card))
    }

}