package com.donghyukki.korello.presentation.dto

class TodoDTO {

    companion object {
        data class Create(
            val cardId: String,
            val title: String,
        )

        data class Delete(
            val id: String,
        )

        data class Update(
            val title: String
        )

        data class Response(
            val todoId: String,
            val title: String,
            val status: Boolean,
        )
    }
}