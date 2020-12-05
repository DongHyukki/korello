package com.donghyukki.korello.domain.card.controller

import com.donghyukki.korello.domain.card.service.CardService
import org.springframework.web.bind.annotation.RestController

@RestController
class CardController(
    private val cardService: CardService
) {

}