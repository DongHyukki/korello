package com.donghyukki.korello.presentation.controller.board

import com.donghyukki.korello.application.services.card.CardTodoService
import com.donghyukki.korello.application.services.todo.TodoCrudService
import com.donghyukki.korello.presentation.dto.TodoDTO.Companion.Create
import com.donghyukki.korello.presentation.dto.TodoDTO.Companion.Update
import com.donghyukki.korello.presentation.dto.response.KorelloResponse
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class CardTodoController(
    private val cardTodoService: CardTodoService,
    private val todoCrudService: TodoCrudService
) {

    @Operation(summary = "TODO 조회", description = "특정 CARD의 TODO를 조회 한다")
    @GetMapping("api/v1/card/{id}/todo")
    fun getTodosByCardId(@PathVariable id: String): KorelloResponse {
        return KorelloResponse(cardTodoService.getTodosByCardId(id))
    }

    @Operation(summary = "TODO 생성", description = "특정 CARD의 TODO를 생성 한다")
    @PostMapping("api/v1/card/{id}/todo")
    fun createTodo(@RequestBody todoCreateDTO: Create, @PathVariable id: String): KorelloResponse {
        val response = cardTodoService.createTodo(id, todoCreateDTO)
        return KorelloResponse(HttpStatus.CREATED, response)
    }

    @Operation(summary = "TODO 삭제", description = "특정 CARD의 특정 TODO를 삭제 한다")
    @DeleteMapping("api/v1/todo/{id}")
    fun deleteTodo(@PathVariable id: String): KorelloResponse {
        todoCrudService.deleteTodo(id)
        return KorelloResponse()
    }

    @Operation(summary = "TODO 수정", description = "특정 TODO를 수정한다.")
    @PutMapping("api/v1/todo/{id}")
    fun updateTodo(@PathVariable id: String, @RequestBody todoUpdateDTO: Update): KorelloResponse {
        todoCrudService.updateTodo(id, todoUpdateDTO)
        return KorelloResponse()
    }

    @Operation(summary = "TODO 상태 변경", description = "특정 TODO의 상태를 변경")
    @PutMapping("api/v1/todo/{id}/status")
    fun changeTodoStatus(@PathVariable id: String): KorelloResponse {
        todoCrudService.changeTodoStatus(id)
        return KorelloResponse()
    }

}