package com.donghyukki.korello.presentation.dto

import com.donghyukki.korello.domain.card.model.Card
import java.io.Serializable
import java.time.LocalDateTime

class CardDTO {
    companion object {
        data class Create(
            val name: String,
            val tagValue: String,
            val members: List<String>?,
            val linkId: Long?
        )

        data class Delete(
            val id: String,
            val isLast: Boolean
        )

        data class UpdateTag(
            val id: String,
            val tagValue: String,
            val displayOrder: String
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
            val dueDate: String
        )

        data class UpdateLinkId(
            val id: String,
            val linkId: Long
        )

        data class UpdateTagAndLinkId(
            val id: String,
            val tagValue: String,
            val linkId: Long
        )

        data class LabelResponse(
            val id: String,
            val name: String,
            val color: String,
            val createDate: LocalDateTime,
            val updateDate: LocalDateTime,
        )

        data class Response(
            val id: String,
            val name: String,
            val tagValue: String,
            val memberNames: List<String>?,
            val labels: List<LabelResponse>,
            val createDate: LocalDateTime,
            val updateDate: LocalDateTime,
            val dueDate: LocalDateTime?,
            val linkId: Long
        ) : Serializable

        fun responseOf(card: Card): Response {
            return Response(
                card.id!!.toString(),
                card.name,
                card.cardTag.tagValue,
                card.members.map { member -> member.name }.toList(),
                card.labels.map { label ->
                    CardDTO.Companion.LabelResponse(
                        label.id.toString(),
                        label.name,
                        label.color,
                        label.createDate,
                        label.updateDate
                    )
                },
                card.createDate,
                card.updateDate,
                card.dueDate,
                card.linkId
            )
        }
    }
}
