package com.donghyukki.korello.presentation.controller.board

import com.donghyukki.korello.application.services.CardLabelService
import com.donghyukki.korello.presentation.dto.LabelDTO.Companion.AddCard
import com.donghyukki.korello.presentation.dto.LabelDTO.Companion.Delete
import com.donghyukki.korello.presentation.dto.LabelDTO.Companion.Update
import com.donghyukki.korello.presentation.dto.response.KorelloResponse
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class CardLabelController(
    private val cardLabelService: CardLabelService
) {

    @Operation(summary = "Label 추가", description = "특정 CARD의 LABEL을 추가한다.")
    @PostMapping("api/v1/card/{id}/label")
    fun addLabelToCard(@RequestBody labelAddCardDTO: AddCard, @PathVariable id: String): KorelloResponse {
        cardLabelService.addLabelToCard(id, labelAddCardDTO)
        return KorelloResponse(HttpStatus.CREATED)
    }

    @Operation(summary = "Label 삭제", description = "특정 CARD의 LABEL을 삭제한다.")
    @PostMapping("api/v1/card/{id}/label/delete")
    fun deleteLabelToCard(@RequestBody labelDeleteDTO: Delete, @PathVariable id: String): KorelloResponse {
        cardLabelService.deleteLabelFromCard(id, labelDeleteDTO)
        return KorelloResponse(HttpStatus.OK)
    }

    @Operation(summary = "Label 수정", description = "특정 LABEL을 수정한다.")
    @PutMapping("api/v1/label/{id}")
    fun updateLabel(@RequestBody labelUpdateDTO: Update, @PathVariable id: String): KorelloResponse {
        cardLabelService.updateLabel(id, labelUpdateDTO)
        return KorelloResponse(HttpStatus.OK)
    }

}