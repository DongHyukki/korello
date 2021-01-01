package com.donghyukki.korello.application.services

import com.donghyukki.korello.domain.board.repository.BoardRepository
import com.donghyukki.korello.domain.label.model.Label
import com.donghyukki.korello.domain.label.repository.LabelRepository
import com.donghyukki.korello.presentation.dto.LabelDTO
import com.donghyukki.korello.presentation.dto.LabelDTO.Companion.Create
import com.donghyukki.korello.presentation.dto.LabelDTO.Companion.Response
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardLabelService(
    private val boardRepository: BoardRepository,
    private val labelRepository: LabelRepository,
) {

    @Transactional(readOnly = true)
    fun getBoardLabels(boardId: String): List<Response> {
        return labelRepository.getLabelsByBoardId(boardId.toLong()).map {
                label -> Response(label.id.toString(), label.name, label.color, label.createDate, label.updateDate ) }.toList()
    }

    @Transactional
    fun createLabel(boardId: String, labelCreateDTO: Create): Label {
        val board = boardRepository.findById(boardId.toLong()).orElseThrow()
        return labelRepository.save(Label(board, labelCreateDTO.name, labelCreateDTO.color))
    }

    @Transactional
    fun clearLabel(boardId: String) {
        return labelRepository.getLabelsByBoardId(boardId.toLong()).forEach { label -> label.clearBoard() }
    }


}