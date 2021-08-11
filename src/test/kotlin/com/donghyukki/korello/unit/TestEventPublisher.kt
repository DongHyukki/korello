package com.donghyukki.korello.unit

import com.donghyukki.korello.application.port.KorelloEventPublisher
import com.donghyukki.korello.presentation.dto.EventDTO

class TestEventPublisher : KorelloEventPublisher {

    override fun publishEvent(eventDTO: EventDTO) {

    }
}
