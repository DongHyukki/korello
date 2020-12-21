package com.donghyukki.korello.application.services

import com.donghyukki.korello.domain.card.repository.CardRepository
import com.donghyukki.korello.domain.label.repository.LabelRepository
import com.donghyukki.korello.presentation.dto.LabelDTO
import com.donghyukki.korello.presentation.dto.LabelDTO.Companion.AddCard
import com.donghyukki.korello.presentation.dto.LabelDTO.Companion.Delete
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CardLabelService(
    private val cardRepository: CardRepository,
    private val labelRepository: LabelRepository,
) {
    @Transactional
    fun updateLabel(labelId: String, labelUpdateDTO: LabelDTO.Companion.Update) {
        val label = labelRepository.findById(labelId.toLong()).orElseThrow()
        label.changeName(labelUpdateDTO.name)
        label.changeColor(labelUpdateDTO.color)
    }

    @Transactional
    fun addLabelToCard(cardId: String, labelAddCardDTO: AddCard) {
        val card = cardRepository.findById(cardId.toLong()).orElseThrow()
        val label = labelRepository.findById(labelAddCardDTO.labelId.toLong()).orElseThrow()
        label.addCard(card)
        card.addLabels(listOf(label))
    }

    @Transactional
    fun deleteLabelFromCard(cardId: String, labelDeleteDTO: Delete) {
        val card = cardRepository.findById(cardId.toLong()).orElseThrow()
        val labelIds = labelDeleteDTO.labelIds.map { s -> s.toLong() }.toLongArray().asIterable()
        val labels = labelRepository.findAllById(labelIds)
        labels.forEach { label -> label.deleteCard(card) }
        card.deleteLabels(labels)
    }

}