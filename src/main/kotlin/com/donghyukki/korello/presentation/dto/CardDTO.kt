package com.donghyukki.korello.presentation.dto

import com.donghyukki.korello.domain.label.model.Label
import java.time.LocalDateTime

class CardDTO {
    companion object {
        data class Create(
            val name: String,
            val tagValue: String,
            val members: List<String>?
        )

        data class Delete(
            val id: String,
        )

        data class UpdateTag(
            val id: String,
            val tagValue: String,
        )

        data class UpdateName(
            val id: String,
            val name: String,
        )

        data class UpdateMembers(
            val id: String,
            val memberNames: List<String>,
        )

        data class UpdateDueDate(
            val id: String,
            val dueDate: LocalDateTime
        )

        data class LabelResponse(
            val id: String,
            val name: String,
            val color: String,
            val createDate: LocalDateTime,
            val updateDate: LocalDateTime
        )

        data class Response(
            val id: String,
            val name: String,
            val tagValue: String,
            val memberNames: List<String>?,
            val labels: List<LabelResponse>,
            val createDate: LocalDateTime,
            val updateDate: LocalDateTime
        ) {
        }
    }
}