package com.donghyukki.korello.presentation.controller.member

import com.donghyukki.korello.presentation.dto.response.KorelloResponse
import com.donghyukki.korello.presentation.dto.MemberDTO.Companion.Create
import com.donghyukki.korello.presentation.dto.MemberDTO.Companion.Delete
import com.donghyukki.korello.domain.member.service.MemberCrudService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class MemberCrudController(
    private val memberCrudService: MemberCrudService
) {

    @Operation(summary = "MEMBER 조회", description = "모든 MEMBER를 조회 한다")
    @GetMapping("api/v1/members")
    fun getMembers(): KorelloResponse {
        return KorelloResponse(memberCrudService.getAllMembers())
    }

    @Operation(summary = "MEMBER 조회", description = "특정 MEMBER를 조회 한다")
    @GetMapping("api/v1/member/{id}")
    fun getMember(@PathVariable id: String): KorelloResponse {
        return KorelloResponse(memberCrudService.getMember(id.toLong()))
    }

    @Operation(summary = "MEMBER 생성", description = "MEMBER를 생성 한다.")
    @PostMapping("api/v1/member")
    fun createMember(@RequestBody memberCreateDTO: Create): KorelloResponse {
        memberCrudService.createMember(memberCreateDTO)
        return KorelloResponse(HttpStatus.CREATED)
    }

    @Operation(summary = "MEMBER 수정", description = "MEMBER를 수정 한다.")
    @PutMapping("api/v1/member")
    fun updateMember(): KorelloResponse {
        return KorelloResponse(HttpStatus.NOT_IMPLEMENTED,"미구현")
    }

    @Operation(summary = "MEMBER 삭제", description = "MEMBER를 삭제 한다.")
    @PostMapping("api/v1/member/delete")
    fun deleteMember(@RequestBody memberDeleteDTO: Delete): KorelloResponse {
        memberCrudService.deleteMember(memberDeleteDTO)
        return KorelloResponse()
    }
}