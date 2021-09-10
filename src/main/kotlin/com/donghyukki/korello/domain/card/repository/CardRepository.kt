package com.donghyukki.korello.domain.card.repository

import com.donghyukki.korello.domain.card.model.Card
import com.donghyukki.korello.domain.card.model.CardTag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CardRepository : JpaRepository<Card, Long> {

    fun findAllByCardTagAndBoardId(cardTag: CardTag, boardId: Long): List<Card>

}
