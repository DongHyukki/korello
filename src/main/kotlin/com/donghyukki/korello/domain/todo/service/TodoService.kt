package com.donghyukki.korello.domain.todo.service

import com.donghyukki.korello.domain.todo.repository.TodoRepository
import com.donghyukki.korello.infrastructure.exception.KorelloNotFoundException
import com.donghyukki.korello.presentation.dto.EventDTO
import com.donghyukki.korello.presentation.dto.TodoDTO.Companion.Delete
import com.donghyukki.korello.presentation.dto.TodoDTO.Companion.Update
import com.donghyukki.korello.presentation.dto.type.KorelloActionType
import com.donghyukki.korello.presentation.dto.type.KorelloEventType
import com.donghyukki.korello.presentation.dto.type.KorelloSelectType
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TodoService(
    private val todoRepository: TodoRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) {
    @Transactional
    fun deleteTodo(id: String) {
        val findTodo = todoRepository.findById(id.toLong()).orElseThrow { KorelloNotFoundException() }
        val cardId = findTodo.card!!.id
        findTodo.clearCard()
        todoRepository.deleteById(id.toLong())
        applicationEventPublisher.publishEvent(EventDTO(cardId!!, KorelloSelectType.CARD, KorelloEventType.TODO, KorelloActionType.DELETE))
    }

    @Transactional
    fun changeTodoStatus(id: String) {
        val findTodo = todoRepository.findById(id.toLong()).orElseThrow { KorelloNotFoundException() }
        findTodo.changeStatus()
        applicationEventPublisher.publishEvent(EventDTO(findTodo.card!!.id!!, KorelloSelectType.CARD, KorelloEventType.TODO, KorelloActionType.UPDATE))
    }

    @Transactional
    fun updateTodo(id: String, updateTodoDTO: Update) {
        val findTodo = todoRepository.findById(id.toLong()).orElseThrow { KorelloNotFoundException() }
        findTodo.changeTitle(updateTodoDTO.title)
        applicationEventPublisher.publishEvent(EventDTO(findTodo.card!!.id!!, KorelloSelectType.CARD, KorelloEventType.TODO, KorelloActionType.UPDATE))
    }
}