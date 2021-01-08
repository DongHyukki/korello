package com.donghyukki.korello.domain.member.service

import com.donghyukki.korello.presentation.dto.MemberDTO.Companion.Create
import com.donghyukki.korello.presentation.dto.MemberDTO.Companion.Delete
import com.donghyukki.korello.domain.member.model.Member
import com.donghyukki.korello.domain.member.repository.MemberRepository
import com.donghyukki.korello.infrastructure.exception.KorelloNotFoundException
import com.donghyukki.korello.presentation.dto.MemberDTO
import com.donghyukki.korello.presentation.dto.MemberDTO.Companion.Response
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class MemberCrudService(
    val memberRepository: MemberRepository
) {
    @Transactional(readOnly = true)
    fun getMemberEntity(memberId: Long): Member {
        return memberRepository.findById(memberId).orElseThrow { KorelloNotFoundException() }
    }

    @Transactional(readOnly = true)
    fun getAllMembers(): List<Response> {
        return memberRepository.findAll().map { member -> Response(member.id.toString(), member.name) }.toList()
    }

    @Transactional(readOnly = true)
    fun getMember(memberId: Long): Response {
        val member = memberRepository.findById(memberId).orElseThrow { KorelloNotFoundException() }
        return Response(member.id.toString(), member.name)
    }

    fun createMember(memberCreateDTO: Create): Member {
        return memberRepository.save(memberCreateDTO.toEntity())
    }

    fun deleteMember(memberDeleteDTO: Delete) {
        return memberRepository.deleteById(memberDeleteDTO.id.toLong())
    }
}