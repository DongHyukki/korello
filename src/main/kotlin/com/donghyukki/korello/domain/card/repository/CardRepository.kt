package com.donghyukki.korello.domain.card.repository

import com.donghyukki.korello.domain.card.model.Card
import com.donghyukki.korello.domain.card.model.CardTag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

interface CardRepository {
    fun save(card: Card): Card
    fun findById(id: Long): Optional<Card>
    fun findAllByCardTagAndBoardId(cardTag: CardTag, boardId: Long): List<Card>
}
