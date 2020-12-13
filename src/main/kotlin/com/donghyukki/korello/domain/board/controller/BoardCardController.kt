package com.donghyukki.korello.domain.board.controller

import com.donghyukki.korello.core.dto.response.KorelloResponse
import com.donghyukki.korello.domain.board.service.BoardCardService
import com.donghyukki.korello.domain.card.dto.CardDTO
import com.donghyukki.korello.domain.card.dto.CardDTO.Companion.Create
import com.donghyukki.korello.domain.card.dto.CardDTO.Companion.Delete
import com.donghyukki.korello.domain.card.dto.CardDTO.Companion.UpdateMembers
import com.donghyukki.korello.domain.card.dto.CardDTO.Companion.UpdateName
import com.donghyukki.korello.domain.card.dto.CardDTO.Companion.UpdateTag
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
        boardCardService.addCardToBoard(id, cardCreateDTO)
        return KorelloResponse(HttpStatus.CREATED)
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
}