package com.donghyukki.korello.application.services.todo

import com.donghyukki.korello.domain.todo.repository.TodoRepository
import com.donghyukki.korello.infrastructure.exception.KorelloNotFoundException
import com.donghyukki.korello.infrastructure.web.event.publisher.KorelloEventPublisher
import com.donghyukki.korello.presentation.dto.EventDTO
import com.donghyukki.korello.presentation.dto.TodoDTO.Companion.Update
import com.donghyukki.korello.presentation.dto.type.KorelloActionType
import com.donghyukki.korello.presentation.dto.type.KorelloEventType
import com.donghyukki.korello.presentation.dto.type.KorelloSelectType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TodoCrudService(
    private val todoRepository: TodoRepository,
    private val korelloEventPublisher: KorelloEventPublisher
) {
    @Transactional
    fun deleteTodo(id: String) {
        val findTodo = todoRepository.findById(id.toLong()).orElseThrow { KorelloNotFoundException() }
        val cardId = findTodo.card!!.id
        findTodo.clearCard()
        todoRepository.deleteById(id.toLong())
        korelloEventPublisher.publishEvent(
            EventDTO(
                cardId!!,
                KorelloSelectType.CARD,
                KorelloEventType.TODO,
                KorelloActionType.DELETE
            )
        )
    }

    @Transactional
    fun changeTodoStatus(id: String) {
        val findTodo = todoRepository.findById(id.toLong()).orElseThrow { KorelloNotFoundException() }
        findTodo.changeStatus()
        korelloEventPublisher.publishEvent(
            EventDTO(
                findTodo.card!!.id!!,
                KorelloSelectType.CARD,
                KorelloEventType.TODO,
                KorelloActionType.UPDATE
            )
        )
    }

    @Transactional
    fun updateTodo(id: String, updateTodoDTO: Update) {
        val findTodo = todoRepository.findById(id.toLong()).orElseThrow { KorelloNotFoundException() }
        findTodo.changeTitle(updateTodoDTO.title)
        korelloEventPublisher.publishEvent(
            EventDTO(
                findTodo.card!!.id!!,
                KorelloSelectType.CARD,
                KorelloEventType.TODO,
                KorelloActionType.UPDATE
            )
        )
    }
}
