package com.donghyukki.korello.presentation.controller.board

import com.donghyukki.korello.application.services.board.BoardCardService
import com.donghyukki.korello.presentation.dto.CardDTO.Companion.Create
import com.donghyukki.korello.presentation.dto.CardDTO.Companion.Delete
import com.donghyukki.korello.presentation.dto.CardDTO.Companion.UpdateMembers
import com.donghyukki.korello.presentation.dto.CardDTO.Companion.UpdateName
import com.donghyukki.korello.presentation.dto.CardDTO.Companion.UpdateTag
import com.donghyukki.korello.presentation.dto.CardDTO.Companion.UpdateDueDate
import com.donghyukki.korello.presentation.dto.CardDTO.Companion.UpdateOrder
import com.donghyukki.korello.presentation.dto.response.KorelloResponse
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class BoardCardController(
    private val boardCardService: BoardCardService
) {

    @Operation(summary = "CARD 조회", description = "특정 BOARD의 CARD를 조회 한다")
    @GetMapping("api/v1/board/{id}/cards")
    fun getCards(@PathVariable id: String): KorelloResponse {
        return KorelloResponse(boardCardService.getAllCardsById(id))
    }

    @Operation(summary = "CARD 생성", description = "특정 BOARD의 CARD를 생성 한다")
    @PostMapping("api/v1/board/{id}/card")
    fun addCard(@PathVariable id: String, @RequestBody cardCreateDTO: Create): KorelloResponse {
        val response = boardCardService.addCardToBoard(id, cardCreateDTO)
        return KorelloResponse(HttpStatus.CREATED, response)
    }

    @Operation(summary = "CARD 삭제", description = "특정 BOARD의 CARD를 삭제 한다")
    @PostMapping("api/v1/board/{id}/card/delete")
    fun deleteCard(@PathVariable id: String, @RequestBody cardDeleteDTO: Delete): KorelloResponse {
        boardCardService.deleteCardFromBoard(id, cardDeleteDTO)
        return KorelloResponse(HttpStatus.CREATED)
    }

    @Operation(summary = "CARD 태그 수정", description = "특정 CARD의 태그를 수정한다.")
    @PutMapping("api/v1/board/{id}/card/tag")
    fun updateCardTag(@PathVariable id: String, @RequestBody cardUpdateTagDTO: UpdateTag): KorelloResponse {
        boardCardService.updateCardTag(id, cardUpdateTagDTO)
        return KorelloResponse()
    }

    @Operation(summary = "CARD 이름 수정", description = "특정 CARD의 이름을 수정한다.")
    @PutMapping("api/v1/board/{id}/card/name")
    fun updateCardName(@PathVariable id: String, @RequestBody cardUpdateNameDTO: UpdateName): KorelloResponse {
        boardCardService.updateCardName(id, cardUpdateNameDTO)
        return KorelloResponse()
    }

    @Operation(summary = "CARD 멤버 수정", description = "특정 CARD의 멤버들을 수정한다.")
    @PutMapping("api/v1/board/{id}/card/members")
    fun updateCardMembers(@PathVariable id: String, @RequestBody cardUpdateMembersDTO: UpdateMembers): KorelloResponse {
        boardCardService.updateCardMembers(id, cardUpdateMembersDTO)
        return KorelloResponse()
    }

    @Operation(summary = "CARD Due Date 수정", description = "특정 CARD의 Due Date를 수정한다.")
    @PutMapping("api/v1/board/{id}/card/due-date")
    fun updateCardDueDate(@PathVariable id: String, @RequestBody cardUpdateDueDateDTO: UpdateDueDate): KorelloResponse {
        boardCardService.updateCardDueDate(id, cardUpdateDueDateDTO)
        return KorelloResponse()
    }

    @Operation(summary = "CARD 순서 수정", description = "특정 CARD의 순서를 수정한다.")
    @PutMapping("api/v1/board/{id}/card/display-order")
    fun updateCardDisplayOrder(@PathVariable id: String, @RequestBody cardUpdateOrderDTO: UpdateOrder): KorelloResponse {
        boardCardService.updateCardDisplayOrder(id, cardUpdateOrderDTO)
        return KorelloResponse()
    }

    @Operation(summary = "CARD Due Date 삭제", description = "특정 CARD의 Due Date를 삭제한다.")
    @DeleteMapping("api/v1/board/{id}/card/{cardId}/due-date")
    fun deleteCardDueDate(@PathVariable id: String, @PathVariable cardId: String): KorelloResponse {
        boardCardService.deleteCardDueDate(id, cardId)
        return KorelloResponse()
    }

}