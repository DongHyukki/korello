package com.donghyukki.korello.application.port

import com.donghyukki.korello.presentation.dto.EventDTO

interface KorelloEventPublisher {
    fun publishEvent(eventDTO: EventDTO)
}
