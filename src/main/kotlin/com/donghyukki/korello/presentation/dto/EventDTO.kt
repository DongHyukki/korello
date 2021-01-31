package com.donghyukki.korello.presentation.dto

import com.donghyukki.korello.presentation.dto.type.KorelloActionType
import com.donghyukki.korello.presentation.dto.type.KorelloEventType

data class EventDTO(
    val id: Long,
    val type: KorelloEventType,
    val action: KorelloActionType,
    var memberId: Long?,
    var memberName: String?,
    var message: String?,
) {
    constructor(id: Long, type: KorelloEventType, action: KorelloActionType): this(id, type, action,null, null, null)

    fun buildMessage() {
        this@EventDTO.message = "${type.toAddedPostName()} ${action.kor_name} 했습니다."
    }

}