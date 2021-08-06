package com.donghyukki.korello.infrastructure.web.event.publisher

import com.donghyukki.korello.presentation.dto.EventDTO
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class KorelloEventPublisher(
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    fun publishEvent(eventDTO: EventDTO) {
        applicationEventPublisher.publishEvent(eventDTO);
    }

}

