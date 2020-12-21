package com.donghyukki.korello.presentation.controller.board

import com.donghyukki.korello.application.services.BoardLabelService
import com.donghyukki.korello.presentation.dto.LabelDTO.Companion.Create
import com.donghyukki.korello.presentation.dto.response.KorelloResponse
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class BoardLabelController(
    private val boardLabelService: BoardLabelService
) {

    @Operation(summary = "Label 조회", description = "특정 BOARD의 LABEL을 조회한다.")
    @GetMapping("api/v1/board/{id}/label")
    fun getLabels(@PathVariable id: String): KorelloResponse {
        return KorelloResponse(boardLabelService.getBoardLabels(id))
    }

    @Operation(summary = "Label 생성", description = "특정 BOARD의 LABEL을 생성한다.")
    @PostMapping("api/v1/board/{id}/label")
    fun createLabel(@RequestBody labelCreateDTO: Create, @PathVariable id: String): KorelloResponse {
        boardLabelService.createLabel(id, labelCreateDTO)
        return KorelloResponse(HttpStatus.CREATED)
    }
}