package com.donghyukki.korello.application.services

import com.donghyukki.korello.domain.card.repository.CardRepository
import com.donghyukki.korello.domain.label.repository.LabelRepository
import com.donghyukki.korello.infrastructure.exception.KorelloNotFoundException
import com.donghyukki.korello.presentation.dto.EventDTO
import com.donghyukki.korello.presentation.dto.LabelDTO
import com.donghyukki.korello.presentation.dto.LabelDTO.Companion.AddCard
import com.donghyukki.korello.presentation.dto.LabelDTO.Companion.Delete
import com.donghyukki.korello.presentation.dto.type.KorelloActionType
import com.donghyukki.korello.presentation.dto.type.KorelloEventType
import com.donghyukki.korello.presentation.dto.type.KorelloSelectType
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CardLabelService(
    private val cardRepository: CardRepository,
    private val labelRepository: LabelRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) {
    @Transactional
    fun updateLabel(labelId: String, labelUpdateDTO: LabelDTO.Companion.Update) {
        val label = labelRepository.findById(labelId.toLong()).orElseThrow { KorelloNotFoundException() }
        label.changeName(labelUpdateDTO.name)
        label.changeColor(labelUpdateDTO.color)
        applicationEventPublisher.publishEvent(EventDTO(label.board!!.id!!, KorelloSelectType.BOARD, KorelloEventType.LABEL, KorelloActionType.UPDATE))
    }

    @Transactional
    fun addLabelToCard(cardId: String, labelAddCardDTO: AddCard) {
        val card = cardRepository.findById(cardId.toLong()).orElseThrow { KorelloNotFoundException() }
        val label = labelRepository.findById(labelAddCardDTO.labelId.toLong()).orElseThrow { KorelloNotFoundException() }
        label.addCard(card)
        card.addLabels(listOf(label))
        applicationEventPublisher.publishEvent(EventDTO(card.id!!, KorelloSelectType.CARD, KorelloEventType.LABEL, KorelloActionType.ADD))
    }

    @Transactional
    fun deleteLabelFromCard(cardId: String, labelDeleteDTO: Delete) {
        val card = cardRepository.findById(cardId.toLong()).orElseThrow { KorelloNotFoundException() }
        val labelIds = labelDeleteDTO.labelIds.map { s -> s.toLong() }.toLongArray().asIterable()
        val labels = labelRepository.findAllById(labelIds)
        labels.forEach { label -> label.deleteCard(card) }
        card.deleteLabels(labels)
        applicationEventPublisher.publishEvent(EventDTO(card.id!!, KorelloSelectType.CARD, KorelloEventType.LABEL, KorelloActionType.DELETE))
    }

}