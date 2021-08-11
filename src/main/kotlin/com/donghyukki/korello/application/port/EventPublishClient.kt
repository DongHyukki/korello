package com.donghyukki.korello.application.port

import com.donghyukki.korello.presentation.dto.EventDTO

interface EventPublishClient {
    fun sendAsyncMessage(eventDTO: EventDTO)
}
