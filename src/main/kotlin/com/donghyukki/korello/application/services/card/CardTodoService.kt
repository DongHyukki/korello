package com.donghyukki.korello.application.services.card

import com.donghyukki.korello.application.port.KorelloEventPublisher
import com.donghyukki.korello.domain.card.repository.CardRepository
import com.donghyukki.korello.domain.todo.model.Todo
import com.donghyukki.korello.domain.todo.repository.TodoRepository
import com.donghyukki.korello.infrastructure.exception.KorelloNotFoundException
import com.donghyukki.korello.presentation.dto.EventDTO
import com.donghyukki.korello.presentation.dto.TodoDTO.Companion.Create
import com.donghyukki.korello.presentation.dto.TodoDTO.Companion.Response
import com.donghyukki.korello.presentation.dto.type.KorelloActionType
import com.donghyukki.korello.presentation.dto.type.KorelloEventType
import com.donghyukki.korello.presentation.dto.type.KorelloSelectType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CardTodoService(
    private val cardRepository: CardRepository,
    private val todoRepository: TodoRepository,
    private val korelloEventPublisher: KorelloEventPublisher
) {

    @Transactional(readOnly = true)
    fun getTodosByCardId(cardId: String): List<Response> {
        return todoRepository.getTodosByCardId(cardId.toLong())
            .map { todo -> Response(todo.id.toString(), todo.title, todo.status) }.toList()
    }

    @Transactional
    fun createTodo(cardId: String, todoCreateDTO: Create): Response {
        val card = cardRepository.findById(cardId.toLong()).orElseThrow { KorelloNotFoundException() }
        val todo = Todo(todoCreateDTO.title, card)
        card.addTodo(todo)
        val savedTodo = todoRepository.save(todo)
        korelloEventPublisher.publishEvent(
            EventDTO(
                card.id!!,
                KorelloSelectType.CARD,
                KorelloEventType.TODO,
                KorelloActionType.CREATE
            )
        )
        return Response(savedTodo.id.toString(), savedTodo.title, savedTodo.status)
    }
}
