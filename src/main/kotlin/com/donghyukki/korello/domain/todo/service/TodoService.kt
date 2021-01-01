package com.donghyukki.korello.domain.todo.service

import com.donghyukki.korello.domain.todo.repository.TodoRepository
import com.donghyukki.korello.presentation.dto.TodoDTO.Companion.Delete
import com.donghyukki.korello.presentation.dto.TodoDTO.Companion.Update
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TodoService(
    val todoRepository: TodoRepository
) {
    @Transactional
    fun deleteTodo(id: String) {
        todoRepository.findById(id.toLong()).orElseThrow().clearCard()
        todoRepository.deleteById(id.toLong())
    }

    @Transactional
    fun changeTodoStatus(id: String) {
        todoRepository.findById(id.toLong()).orElseThrow().changeStatus()
    }

    @Transactional
    fun updateTodo(id: String, updateTodoDTO: Update) {
        val todo = todoRepository.findById(id.toLong()).orElseThrow()
        todo.changeTitle(updateTodoDTO.title)
    }
}