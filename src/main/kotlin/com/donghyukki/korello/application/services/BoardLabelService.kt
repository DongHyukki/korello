package com.donghyukki.korello.application.services

import com.donghyukki.korello.domain.board.repository.BoardRepository
import com.donghyukki.korello.domain.label.model.Label
import com.donghyukki.korello.domain.label.repository.LabelRepository
import com.donghyukki.korello.infrastructure.exception.KorelloNotFoundException
import com.donghyukki.korello.presentation.dto.EventDTO
import com.donghyukki.korello.presentation.dto.LabelDTO
import com.donghyukki.korello.presentation.dto.LabelDTO.Companion.Create
import com.donghyukki.korello.presentation.dto.LabelDTO.Companion.Response
import com.donghyukki.korello.presentation.dto.type.KorelloActionType
import com.donghyukki.korello.presentation.dto.type.KorelloEventType
import com.donghyukki.korello.presentation.dto.type.KorelloSelectType
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardLabelService(
    private val boardRepository: BoardRepository,
    private val labelRepository: LabelRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    @Transactional(readOnly = true)
    fun getBoardLabels(boardId: String): List<Response> {
        return labelRepository.getLabelsByBoardId(boardId.toLong())
            .map { label -> Response(label.id.toString(), label.name, label.color, label.createDate, label.updateDate) }
            .toList()
    }

    @Transactional
    fun createLabel(boardId: String, labelCreateDTO: Create): Label {
        val board = boardRepository.findById(boardId.toLong()).orElseThrow { KorelloNotFoundException() }
        val label = labelRepository.save(Label(board, labelCreateDTO.name, labelCreateDTO.color))
        applicationEventPublisher.publishEvent(EventDTO(board.id!!, KorelloSelectType.BOARD, KorelloEventType.LABEL, KorelloActionType.CREATE))
        return label
    }

    @Transactional
    fun clearLabel(boardId: String) {
        val label =  labelRepository.getLabelsByBoardId(boardId.toLong()).forEach { label -> label.clearBoard() }
        applicationEventPublisher.publishEvent(EventDTO(boardId.toLong(), KorelloSelectType.BOARD, KorelloEventType.LABEL, KorelloActionType.UPDATE))
        return label
    }


}