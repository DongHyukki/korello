package com.donghyukki.korello.application.services.board

import com.donghyukki.korello.application.port.KorelloEventPublisher
import com.donghyukki.korello.domain.board.repository.BoardRepository
import com.donghyukki.korello.domain.card.model.Card
import com.donghyukki.korello.domain.card.repository.CardRepository
import com.donghyukki.korello.domain.member.model.Member
import com.donghyukki.korello.infrastructure.exception.KorelloNotFoundException
import com.donghyukki.korello.presentation.dto.CardDTO
import com.donghyukki.korello.presentation.dto.CardDTO.Companion.Create
import com.donghyukki.korello.presentation.dto.CardDTO.Companion.Delete
import com.donghyukki.korello.presentation.dto.CardDTO.Companion.Response
import com.donghyukki.korello.presentation.dto.CardDTO.Companion.UpdateDisplayOrder
import com.donghyukki.korello.presentation.dto.CardDTO.Companion.UpdateDueDate
import com.donghyukki.korello.presentation.dto.CardDTO.Companion.UpdateMembers
import com.donghyukki.korello.presentation.dto.CardDTO.Companion.UpdateName
import com.donghyukki.korello.presentation.dto.CardDTO.Companion.UpdateTag
import com.donghyukki.korello.presentation.dto.CardDTO.Companion.UpdateTagAndDisplayOrder
import com.donghyukki.korello.presentation.dto.EventDTO
import com.donghyukki.korello.presentation.dto.type.KorelloActionType
import com.donghyukki.korello.presentation.dto.type.KorelloEventType
import com.donghyukki.korello.presentation.dto.type.KorelloSelectType
import org.springframework.cache.annotation.CacheEvict
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Service
class BoardCardService(
    private val boardRepository: BoardRepository,
    private val cardRepository: CardRepository,
    private val korelloEventPublisher: KorelloEventPublisher
) {

    @Transactional(readOnly = true)
    fun getAllCardsById(boardId: String): List<Response> {
        val board = boardRepository.findById(boardId.toLong()).orElseThrow { KorelloNotFoundException() }

        return board.cards.map { card -> CardDTO.responseOf(card) }.toList()
    }

    @CacheEvict(value = ["board"], key = "#boardId")
    @Transactional
    fun addCardToBoard(boardId: String, cardCreateDTO: Create): Response {
        val board = boardRepository.findById(boardId.toLong()).orElseThrow { KorelloNotFoundException() }
        val members: List<Member> = arrayListOf()
        if (cardCreateDTO.members != null) {
            members.plus(board.members.filter { boardMembers -> cardCreateDTO.members.contains(boardMembers.member.name) })
        }
        val savedCard =
            cardRepository.save(Card(cardCreateDTO.name, cardCreateDTO.tagValue, board, members, cardCreateDTO.order))
        board.addCard(savedCard)
        korelloEventPublisher.publishEvent(
            EventDTO(
                board.id!!,
                KorelloSelectType.BOARD,
                KorelloEventType.CARD,
                KorelloActionType.ADD
            )
        )

        return CardDTO.responseOf(savedCard)
    }

    @CacheEvict(value = ["board"], key = "#boardId")
    @Transactional
    fun deleteCardFromBoard(boardId: String, cardDeleteDTO: Delete) {
        val board = boardRepository.findById(boardId.toLong()).orElseThrow { KorelloNotFoundException() }
        val card = cardRepository.findById(cardDeleteDTO.id.toLong()).orElseThrow { KorelloNotFoundException() }
        card.clearLabels()
        board.deleteCard(cardDeleteDTO.id.toLong())
        korelloEventPublisher.publishEvent(
            EventDTO(
                board.id!!,
                KorelloSelectType.BOARD,
                KorelloEventType.CARD,
                KorelloActionType.DELETE
            )
        )
    }

    @CacheEvict(value = ["board"], key = "#boardId")
    @Transactional
    fun updateCardTag(boardId: String, cardUpdateTagDTO: UpdateTag) {
        val board = boardRepository.findById(boardId.toLong()).orElseThrow { KorelloNotFoundException() }
        val card =
            board.cards.firstOrNull { card -> card.id == cardUpdateTagDTO.id.toLong() } ?: throw KorelloNotFoundException()
        card.changeTag(cardUpdateTagDTO.tagValue)
        korelloEventPublisher.publishEvent(
            EventDTO(
                card.id!!,
                KorelloSelectType.CARD,
                KorelloEventType.CARD,
                KorelloActionType.UPDATE
            )
        )
    }

    @CacheEvict(value = ["board"], key = "#boardId")
    @Transactional
    fun updateCardDisplayOrder(boardId: String, cardUpdateDisplayOrderDTO: UpdateDisplayOrder) {
        val board = boardRepository.findById(boardId.toLong()).orElseThrow { KorelloNotFoundException() }
        val updateCard =
            board.cards.firstOrNull { card -> card.id == cardUpdateDisplayOrderDTO.id.toLong() } ?: throw KorelloNotFoundException()
        val displayOrder = cardUpdateDisplayOrderDTO.displayOrder.toInt()
        updateCard.changeDisplayOrder(displayOrder)
        korelloEventPublisher.publishEvent(
            EventDTO(
                updateCard.id!!,
                KorelloSelectType.CARD,
                KorelloEventType.CARD,
                KorelloActionType.UPDATE
            )
        )
    }

    @CacheEvict(value = ["board"], key = "#boardId")
    @Transactional
    fun updateCardTagAndDisplayOrder(boardId: String, updateTagAndDisplayOrderDTO: UpdateTagAndDisplayOrder) {
        val board = boardRepository.findById(boardId.toLong()).orElseThrow { KorelloNotFoundException() }
        val updateCard =
            board.cards.firstOrNull { card -> card.id == updateTagAndDisplayOrderDTO.id.toLong() } ?: throw KorelloNotFoundException()
        val displayOrder = updateTagAndDisplayOrderDTO.displayOrder.toInt()
        updateCard.changeTagAndDisplayOrder(updateTagAndDisplayOrderDTO.tagValue, displayOrder)
        korelloEventPublisher.publishEvent(
            EventDTO(
                updateCard.id!!,
                KorelloSelectType.CARD,
                KorelloEventType.CARD,
                KorelloActionType.UPDATE
            )
        )
    }

    @CacheEvict(value = ["board"], key = "#boardId")
    @Transactional
    fun updateCardName(boardId: String, cardUpdateNameDTO: UpdateName) {
        val board = boardRepository.findById(boardId.toLong()).orElseThrow { KorelloNotFoundException() }
        val card =
            board.cards.firstOrNull { card -> card.id == cardUpdateNameDTO.id.toLong() } ?: throw KorelloNotFoundException()
        card.changeName(cardUpdateNameDTO.name)
        korelloEventPublisher.publishEvent(
            EventDTO(
                card.id!!,
                KorelloSelectType.CARD,
                KorelloEventType.CARD,
                KorelloActionType.UPDATE
            )
        )
    }

    @CacheEvict(value = ["board"], key = "#boardId")
    @Transactional
    fun updateCardMembers(boardId: String, cardUpdateMembersDTO: UpdateMembers) {
        val board = boardRepository.findById(boardId.toLong()).orElseThrow { KorelloNotFoundException() }
        val card =
            board.cards.firstOrNull { card -> card.id == cardUpdateMembersDTO.id.toLong() } ?: throw KorelloNotFoundException()
        val joinMembers = board.members.map { boardMembers -> boardMembers.member }.toList()
        val updateMembers = joinMembers.filter { member -> cardUpdateMembersDTO.memberNames.contains(member.name) }
        card.changeMembers(updateMembers)
        korelloEventPublisher.publishEvent(
            EventDTO(
                card.id!!,
                KorelloSelectType.CARD,
                KorelloEventType.CARD,
                KorelloActionType.UPDATE
            )
        )
    }

    @CacheEvict(value = ["board"], key = "#boardId")
    @Transactional
    fun updateCardDueDate(boardId: String, cardUpdateDueDateDTO: UpdateDueDate) {
        val board = boardRepository.findById(boardId.toLong()).orElseThrow { KorelloNotFoundException() }
        val card =
            board.cards.firstOrNull { card -> card.id == cardUpdateDueDateDTO.id.toLong() } ?: throw KorelloNotFoundException()
        val dueDate = LocalDateTime.parse(cardUpdateDueDateDTO.dueDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        card.changeDueDate(dueDate)
        korelloEventPublisher.publishEvent(
            EventDTO(
                card.id!!,
                KorelloSelectType.CARD,
                KorelloEventType.CARD,
                KorelloActionType.UPDATE
            )
        )
    }

    @CacheEvict(value = ["board"], key = "#boardId")
    @Transactional
    fun deleteCardDueDate(boardId: String, cardId: String) {
        val board = boardRepository.findById(boardId.toLong()).orElseThrow { KorelloNotFoundException() }
        val card = board.cards.firstOrNull { card -> card.id == cardId.toLong() } ?: throw KorelloNotFoundException()
        card.deleteDueDate()
        korelloEventPublisher.publishEvent(
            EventDTO(
                card.id!!,
                KorelloSelectType.CARD,
                KorelloEventType.CARD,
                KorelloActionType.UPDATE
            )
        )
    }

}
