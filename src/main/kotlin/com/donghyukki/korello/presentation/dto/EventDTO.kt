package com.donghyukki.korello.presentation.dto

import com.donghyukki.korello.presentation.dto.type.KorelloActionType
import com.donghyukki.korello.presentation.dto.type.KorelloEventType
import com.donghyukki.korello.presentation.dto.type.KorelloSelectType

data class EventDTO(
    val id: Long,
    val selectType: KorelloSelectType,
    val eventType: KorelloEventType,
    val action: KorelloActionType,
    var memberId: Long?,
    var memberName: String?,
    var message: String?,
) {
    constructor(id: Long, selectType: KorelloSelectType, eventType: KorelloEventType, action: KorelloActionType) : this(
        id,
        selectType,
        eventType,
        action,
        null,
        null,
        null
    )

    fun buildMessage() {
        this@EventDTO.message = "${eventType.toAddedPostName()} ${action.kor_name} 했습니다."
    }

}
