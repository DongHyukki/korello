package com.donghyukki.korello.application.services.board

import com.donghyukki.korello.application.port.KorelloEventPublisher
import com.donghyukki.korello.domain.board.repository.BoardRepository
import com.donghyukki.korello.domain.label.model.Label
import com.donghyukki.korello.domain.label.repository.LabelRepository
import com.donghyukki.korello.infrastructure.exception.KorelloNotFoundException
import com.donghyukki.korello.presentation.dto.EventDTO
import com.donghyukki.korello.presentation.dto.LabelDTO.Companion.Create
import com.donghyukki.korello.presentation.dto.LabelDTO.Companion.Response
import com.donghyukki.korello.presentation.dto.type.KorelloActionType
import com.donghyukki.korello.presentation.dto.type.KorelloEventType
import com.donghyukki.korello.presentation.dto.type.KorelloSelectType
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardLabelService(
    private val boardRepository: BoardRepository,
    private val labelRepository: LabelRepository,
    private val korelloEventPublisher: KorelloEventPublisher
) {

    @Cacheable(value = ["label"], key = "#boardId")
    @Transactional(readOnly = true)
    fun getBoardLabels(boardId: String): List<Response> {
        return labelRepository.getLabelsByBoardId(boardId.toLong())
            .map { label -> Response(label.id.toString(), label.name, label.color, label.createDate, label.updateDate) }
            .toList()
    }

    @CacheEvict(value = ["label"], key = "#boardId")
    @Transactional
    fun createLabel(boardId: String, labelCreateDTO: Create): Response {
        val board = boardRepository.findById(boardId.toLong()).orElseThrow { KorelloNotFoundException() }
        val label = labelRepository.save(Label(board, labelCreateDTO.name, labelCreateDTO.color))
        korelloEventPublisher.publishEvent(
            EventDTO(
                board.id!!,
                KorelloSelectType.BOARD,
                KorelloEventType.LABEL,
                KorelloActionType.CREATE
            )
        )
        return Response(label.id.toString(), label.name, label.color, label.createDate, label.updateDate)
    }

    @CacheEvict(value = ["label"], key = "#boardId")
    @Transactional
    fun clearLabel(boardId: String) {
        val label = labelRepository.getLabelsByBoardId(boardId.toLong()).forEach { label -> label.clearBoard() }
        korelloEventPublisher.publishEvent(
            EventDTO(
                boardId.toLong(),
                KorelloSelectType.BOARD,
                KorelloEventType.LABEL,
                KorelloActionType.UPDATE
            )
        )
        return label
    }

    @CacheEvict(value = ["label"], key = "#boardId")
    @Transactional
    fun deleteLabel(boardId: String, labelId: String) {
        labelRepository.deleteById(labelId.toLong())
        korelloEventPublisher.publishEvent(
            EventDTO(
                boardId.toLong(),
                KorelloSelectType.BOARD,
                KorelloEventType.LABEL,
                KorelloActionType.DELETE
            )
        )
    }

}
