package com.donghyukki.korello.domain.member.service

import com.donghyukki.korello.presentation.dto.MemberDTO.Companion.Create
import com.donghyukki.korello.presentation.dto.MemberDTO.Companion.Delete
import com.donghyukki.korello.domain.member.model.Member
import com.donghyukki.korello.domain.member.repository.MemberRepository
import com.donghyukki.korello.presentation.dto.MemberDTO
import com.donghyukki.korello.presentation.dto.MemberDTO.Companion.Response
import org.springframework.stereotype.Service
import java.util.*

@Service
class MemberCrudService(
    val memberRepository: MemberRepository
) {
    fun getMemberEntity(memberId: Long): Member {
        return memberRepository.findById(memberId).orElseThrow { IllegalArgumentException("Member Not Existed") }
    }

    fun getAllMembers(): List<Response> {
        return memberRepository.findAll().map { member -> Response(member.id.toString(), member.name) }.toList()
    }

    fun getMember(memberId: Long): Response {
        val member = memberRepository.findById(memberId).orElseThrow { IllegalArgumentException("Member Not Existed") }
        return Response(member.id.toString(), member.name)
    }

    fun createMember(memberCreateDTO: Create): Member {
        return memberRepository.save(memberCreateDTO.toEntity())
    }

    fun deleteMember(memberDeleteDTO: Delete) {
        return memberRepository.deleteById(memberDeleteDTO.id.toLong())
    }
}