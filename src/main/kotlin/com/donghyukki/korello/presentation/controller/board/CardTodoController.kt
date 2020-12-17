package com.donghyukki.korello.presentation.controller.board

import com.donghyukki.korello.application.services.CardTodoService
import com.donghyukki.korello.presentation.dto.TodoDTO.Companion.Create
import com.donghyukki.korello.presentation.dto.response.KorelloResponse
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class CardTodoController(
    private val cardTodoService: CardTodoService
) {

    @Operation(summary = "TODO 생성", description = "특정 CARD의 TODO를 생성 한다")
    @PostMapping("api/v1/card/{id}/todo")
    fun createTodo(@RequestBody todoCreateDTO: Create, @PathVariable id: String): KorelloResponse {
        cardTodoService.createTodo(id, todoCreateDTO)
        return KorelloResponse(HttpStatus.CREATED)
    }

    @Operation(summary = "TODO 조회", description = "특정 CARD의 TODO를 조회 한다")
    @GetMapping("api/v1/card/{id}/todo")
    fun getTodosByCardId(@PathVariable id: String): KorelloResponse {
        return KorelloResponse(cardTodoService.getTodosByCardId(id))
    }

}