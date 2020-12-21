package com.donghyukki.korello.presentation.dto

import java.time.LocalDateTime

class LabelDTO {

    companion object {
        data class Create(
            val name: String,
            val color: String,
        )
        data class AddCard(
            val labelId: String,
        )
        data class Delete(
            val labelIds: List<String>
        )
        data class Update(
            val color: String,
            val name: String,
        )
        data class Response(
            val id: String,
            val name: String,
            val color: String,
            val createDate: LocalDateTime,
            val updateDate: LocalDateTime
        )
    }
}