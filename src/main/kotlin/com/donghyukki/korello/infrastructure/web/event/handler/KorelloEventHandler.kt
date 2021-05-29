package com.donghyukki.korello.infrastructure.web.event.handler

import com.donghyukki.korello.infrastructure.kafka.client.KafkaClient
import com.donghyukki.korello.infrastructure.security.model.MemberAuthentication
import com.donghyukki.korello.presentation.dto.EventDTO
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
class KorelloEventHandler(
    private val memberAuthentication: MemberAuthentication,
    private val kafkaClient: KafkaClient
) {

    @TransactionalEventListener
    fun listenKorelloEvent(event: EventDTO) {
        setMemberInfo(event)
        event.buildMessage()
        kafkaClient.sendAsyncMessage(event)
    }

    private fun setMemberInfo(event: EventDTO) {
        event.memberId = memberAuthentication.getMemberId()
        event.memberName = memberAuthentication.getMemberName()
    }

}