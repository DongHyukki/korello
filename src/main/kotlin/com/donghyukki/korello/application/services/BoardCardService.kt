package com.donghyukki.korello.application.services

import com.donghyukki.korello.domain.board.repository.BoardRepository
import com.donghyukki.korello.domain.card.model.Card
import com.donghyukki.korello.domain.card.repository.CardRepository
import com.donghyukki.korello.domain.card.service.CardCrudService
import com.donghyukki.korello.domain.member.model.Member
import com.donghyukki.korello.infrastructure.exception.KorelloNotFoundException
import com.donghyukki.korello.presentation.dto.CardDTO.Companion.Create
import com.donghyukki.korello.presentation.dto.CardDTO.Companion.Delete
import com.donghyukki.korello.presentation.dto.CardDTO.Companion.Response
import com.donghyukki.korello.presentation.dto.CardDTO.Companion.UpdateDueDate
import com.donghyukki.korello.presentation.dto.CardDTO.Companion.UpdateMembers
import com.donghyukki.korello.presentation.dto.CardDTO.Companion.UpdateName
import com.donghyukki.korello.presentation.dto.CardDTO.Companion.UpdateTag
import com.donghyukki.korello.presentation.dto.CardDTO.Companion.UpdateOrder
import com.donghyukki.korello.presentation.dto.EventDTO
import com.donghyukki.korello.presentation.dto.type.KorelloActionType
import com.donghyukki.korello.presentation.dto.type.KorelloEventType
import com.donghyukki.korello.presentation.dto.type.KorelloSelectType
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Service
class BoardCardService(
    private val boardRepository: BoardRepository,
    private val cardRepository: CardRepository,
    private val cardService: CardCrudService,
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    @Transactional(readOnly = true)
    fun getAllCardsById(boardId: String): List<Response> {
        val board = boardRepository.findById(boardId.toLong()).orElseThrow { KorelloNotFoundException() }
        return board.cards.map { card ->
            card.toResponse()
        }.toList()
    }

    @Transactional
    fun addCardToBoard(boardId: String, cardCreateDTO: Create): Response {
        val board = boardRepository.findById(boardId.toLong()).orElseThrow { KorelloNotFoundException() }
        val members: List<Member> = arrayListOf()
        if (cardCreateDTO.members != null) {
            members.plus(board.members.filter { boardMembers -> cardCreateDTO.members.contains(boardMembers.member.name) })
        }
        val savedCard = cardRepository.save(Card(cardCreateDTO.name, cardCreateDTO.tagValue, board, members, cardCreateDTO.order))
        board.addCard(savedCard)
        applicationEventPublisher.publishEvent(EventDTO(board.id!!, KorelloSelectType.BOARD, KorelloEventType.CARD, KorelloActionType.ADD))
        return savedCard.toResponse()
    }

    @Transactional
    fun deleteCardFromBoard(boardId: String, cardDeleteDTO: Delete) {
        val board = boardRepository.findById(boardId.toLong()).orElseThrow { KorelloNotFoundException() }
        val card = cardRepository.findById(cardDeleteDTO.id.toLong()).orElseThrow { KorelloNotFoundException() }
        card.clearLabels()
        board.deleteCard(cardDeleteDTO.id.toLong())
        applicationEventPublisher.publishEvent(EventDTO(board.id!!, KorelloSelectType.BOARD, KorelloEventType.CARD, KorelloActionType.DELETE))
    }

    @Transactional
    fun updateCardTag(boardId: String, cardUpdateTagDTO: UpdateTag) {
        val board = boardRepository.findById(boardId.toLong()).orElseThrow { KorelloNotFoundException() }
        val card = board.cards.firstOrNull { card -> card.id == cardUpdateTagDTO.id.toLong() }
            ?: throw KorelloNotFoundException()
        cardService.changeTag(card, cardUpdateTagDTO.tagValue)
        applicationEventPublisher.publishEvent(EventDTO(card.id!!, KorelloSelectType.CARD, KorelloEventType.CARD, KorelloActionType.UPDATE))
    }

    @Transactional
    fun updateCardName(boardId: String, cardUpdateNameDTO: UpdateName) {
        val board = boardRepository.findById(boardId.toLong()).orElseThrow { KorelloNotFoundException() }
        val card = board.cards.firstOrNull { card -> card.id == cardUpdateNameDTO.id.toLong() }
            ?: throw KorelloNotFoundException()
        cardService.changeName(card, cardUpdateNameDTO.name)
        applicationEventPublisher.publishEvent(EventDTO(card.id!!, KorelloSelectType.CARD, KorelloEventType.CARD, KorelloActionType.UPDATE))
    }

    @Transactional
    fun updateCardMembers(boardId: String, cardUpdateMembersDTO: UpdateMembers) {
        val board = boardRepository.findById(boardId.toLong()).orElseThrow { KorelloNotFoundException() }
        val card = board.cards.firstOrNull { card -> card.id == cardUpdateMembersDTO.id.toLong() }
            ?: throw KorelloNotFoundException()
        val joinMembers = board.members.map { boardMembers -> boardMembers.member }.toList()
        val updateMembers = joinMembers.filter { member -> cardUpdateMembersDTO.memberNames.contains(member.name) }
        cardService.changeMembers(card, updateMembers)
        applicationEventPublisher.publishEvent(EventDTO(card.id!!, KorelloSelectType.CARD, KorelloEventType.CARD, KorelloActionType.UPDATE))
    }

    @Transactional
    fun updateCardDueDate(boardId: String, cardUpdateDueDateDTO: UpdateDueDate) {
        val board = boardRepository.findById(boardId.toLong()).orElseThrow { KorelloNotFoundException() }
        val card = board.cards.firstOrNull { card -> card.id == cardUpdateDueDateDTO.id.toLong() }
            ?: throw KorelloNotFoundException()
        val dueDate = LocalDateTime.parse(cardUpdateDueDateDTO.dueDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        cardService.changeDueDate(card, dueDate)
        applicationEventPublisher.publishEvent(EventDTO(card.id!!, KorelloSelectType.CARD, KorelloEventType.CARD, KorelloActionType.UPDATE))
    }

    fun updateCardOrder(boardId: String, cardUpdateOrderDTO: UpdateOrder) {
        val board = boardRepository.findById(boardId.toLong()).orElseThrow { KorelloNotFoundException() }
        val updateCard = board.cards.firstOrNull { card -> card.id == cardUpdateOrderDTO.id.toLong() }
            ?: throw KorelloNotFoundException()
        val order = cardUpdateOrderDTO.order.toInt()
        val dbCard = cardService.getCardByOrder(order)

        if (dbCard != null) {
            cardService.changeOrder(dbCard, updateCard.displayOrder)
        }

        cardService.changeOrder(updateCard, order)
        applicationEventPublisher.publishEvent(EventDTO(updateCard.id!!, KorelloSelectType.CARD, KorelloEventType.CARD, KorelloActionType.UPDATE))
    }

    @Transactional
    fun deleteCardDueDate(boardId: String, cardId: String) {
        val board = boardRepository.findById(boardId.toLong()).orElseThrow { KorelloNotFoundException() }
        val card = board.cards.firstOrNull { card -> card.id == cardId.toLong() }
            ?: throw KorelloNotFoundException()
        cardService.deleteCardDueDate(card)
        applicationEventPublisher.publishEvent(EventDTO(card.id!!, KorelloSelectType.CARD, KorelloEventType.CARD, KorelloActionType.UPDATE))
    }

}