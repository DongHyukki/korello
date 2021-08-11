package com.donghyukki.korello.infrastructure.web.event.publisher

import com.donghyukki.korello.application.port.KorelloEventPublisher
import com.donghyukki.korello.presentation.dto.EventDTO
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class SpringEventPublisher(
    private val applicationEventPublisher: ApplicationEventPublisher
) : KorelloEventPublisher {

    override fun publishEvent(eventDTO: EventDTO) {
        applicationEventPublisher.publishEvent(eventDTO);
    }

}

