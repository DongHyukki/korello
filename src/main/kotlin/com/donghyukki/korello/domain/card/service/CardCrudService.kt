package com.donghyukki.korello.domain.card.service

import com.donghyukki.korello.domain.board.model.Board
import com.donghyukki.korello.domain.card.model.Card
import com.donghyukki.korello.domain.card.repository.CardRepository
import com.donghyukki.korello.domain.member.model.Member
import com.donghyukki.korello.infrastructure.exception.KorelloNotFoundException
import com.donghyukki.korello.presentation.dto.CardDTO.Companion.Create
import com.donghyukki.korello.presentation.dto.CardDTO.Companion.Response
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class CardCrudService(
    private val cardRepository: CardRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    @Transactional
    fun createCard(cardCreateDTO: Create, board: Board, members: List<Member>): Card {
       return cardRepository.save(Card(cardCreateDTO.name, cardCreateDTO.tagValue, board, members))
    }

    @Transactional(readOnly = true)
    fun getCardEntity(id: Long): Card {
        return cardRepository.findById(id).orElseThrow { KorelloNotFoundException() }
    }

    @Transactional
    fun changeTag(card: Card, tagValue: String) {
        card.changeTag(tagValue)
    }

    @Transactional
    fun changeName(card: Card, name: String) {
        card.changeName(name)
    }

    @Transactional
    fun changeMembers(card: Card, members: List<Member>) {
        card.changeMembers(members)
    }

    @Transactional
    fun changeDueDate(card: Card, dueDate: LocalDateTime) {
        card.changeDueDate(dueDate)
    }

    @Transactional
    fun deleteCardDueDate(card: Card) {
        card.deleteDueDate()
    }

}