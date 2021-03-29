package com.donghyukki.korello.application.services

import com.donghyukki.korello.domain.board.repository.BoardRepository
import com.donghyukki.korello.domain.card.model.Card
import com.donghyukki.korello.domain.card.repository.CardRepository
import com.donghyukki.korello.domain.member.model.Member
import com.donghyukki.korello.infrastructure.exception.KorelloNotFoundException
import com.donghyukki.korello.presentation.dto.CardDTO.Companion.Create
import com.donghyukki.korello.presentation.dto.CardDTO.Companion.Delete
import com.donghyukki.korello.presentation.dto.CardDTO.Companion.LabelResponse
import com.donghyukki.korello.presentation.dto.CardDTO.Companion.Response
import com.donghyukki.korello.presentation.dto.CardDTO.Companion.UpdateMembers
import com.donghyukki.korello.presentation.dto.CardDTO.Companion.UpdateName
import com.donghyukki.korello.presentation.dto.CardDTO.Companion.UpdateTag
import com.donghyukki.korello.presentation.dto.EventDTO
import com.donghyukki.korello.presentation.dto.type.KorelloActionType
import com.donghyukki.korello.presentation.dto.type.KorelloEventType
import com.donghyukki.korello.presentation.dto.type.KorelloSelectType
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class BoardCardService(
    private val boardRepository: BoardRepository,
    private val cardRepository: CardRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    @Transactional(readOnly = true)
    fun getAllCardsById(boardId: String): List<Response> {
        val board = boardRepository.findById(boardId.toLong()).orElseThrow { KorelloNotFoundException() }
        return board.cards.map { card ->
            Response(
                card.id!!.toString(),
                card.name,
                card.cardTag.tagValue,
                card.members.map { member -> member.name }.toList(),
                card.labels.map { label ->
                    LabelResponse(
                        label.id.toString(),
                        label.name,
                        label.color,
                        label.createDate,
                        label.updateDate
                    )
                },
                card.createDate,
                card.updateDate
            )
        }.toList()
    }

    @Transactional
    fun addCardToBoard(boardId: String, cardCreateDTO: Create) {
        val board = boardRepository.findById(boardId.toLong()).orElseThrow { KorelloNotFoundException() }
        val members: List<Member> = arrayListOf()
        if (cardCreateDTO.members != null) {
            members.plus(board.members.filter { boardMembers -> cardCreateDTO.members.contains(boardMembers.member.name) })
        }
        val savedCard = cardRepository.save(Card(cardCreateDTO.name, cardCreateDTO.tagValue, board, members))
        board.addCard(savedCard)
        applicationEventPublisher.publishEvent(EventDTO(board.id!!, KorelloSelectType.BOARD, KorelloEventType.CARD, KorelloActionType.ADD))
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
        card.changeTag(cardUpdateTagDTO.tagValue)
        applicationEventPublisher.publishEvent(EventDTO(board.id!!, KorelloSelectType.BOARD, KorelloEventType.CARD, KorelloActionType.UPDATE))
    }

    @Transactional
    fun updateCardName(boardId: String, cardUpdateNameDTO: UpdateName) {
        val board = boardRepository.findById(boardId.toLong()).orElseThrow { KorelloNotFoundException() }
        val card = board.cards.firstOrNull { card -> card.id == cardUpdateNameDTO.id.toLong() }
            ?: throw KorelloNotFoundException()
        card.changeName(cardUpdateNameDTO.name)
        applicationEventPublisher.publishEvent(EventDTO(board.id!!, KorelloSelectType.BOARD, KorelloEventType.CARD, KorelloActionType.UPDATE))
    }

    @Transactional
    fun updateCardMembers(boardId: String, cardUpdateMembersDTO: UpdateMembers) {
        val board = boardRepository.findById(boardId.toLong()).orElseThrow { KorelloNotFoundException() }
        val card = board.cards.firstOrNull { card -> card.id == cardUpdateMembersDTO.id.toLong() }
            ?: throw KorelloNotFoundException()
        val joinMembers = board.members.map { boardMembers -> boardMembers.member }.toList()
        val updateMembers = joinMembers.filter { member -> cardUpdateMembersDTO.memberNames.contains(member.name) }
        card.changeMembers(updateMembers)
        applicationEventPublisher.publishEvent(EventDTO(board.id!!, KorelloSelectType.BOARD, KorelloEventType.CARD, KorelloActionType.UPDATE))
    }

}