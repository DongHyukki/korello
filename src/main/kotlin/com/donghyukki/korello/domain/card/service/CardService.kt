package com.donghyukki.korello.domain.card.service

import com.donghyukki.korello.domain.card.repository.CardRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CardService(
    private val cardRepository: CardRepository
) {

    @Transactional
    fun createCard() {

    }
}
