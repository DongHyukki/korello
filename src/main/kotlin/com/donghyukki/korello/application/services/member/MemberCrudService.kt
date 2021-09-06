package com.donghyukki.korello.application.services.member

import com.donghyukki.korello.domain.member.model.Member
import com.donghyukki.korello.domain.member.repository.MemberRepository
import com.donghyukki.korello.infrastructure.exception.KorelloNotFoundException
import com.donghyukki.korello.presentation.dto.MemberDTO
import com.donghyukki.korello.presentation.dto.MemberDTO.Companion.Create
import com.donghyukki.korello.presentation.dto.MemberDTO.Companion.Delete
import com.donghyukki.korello.presentation.dto.MemberDTO.Companion.InfoResponse
import com.donghyukki.korello.presentation.dto.MemberDTO.Companion.Response
import com.donghyukki.korello.presentation.dto.MemberDTO.Companion.Update
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
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

    @CacheEvict(value = ["member"], key = "#memberUpdateDTO.providerId + '::' + #memberUpdateDTO.name")
    @Transactional
    fun changeAuth(memberUpdateDTO: Update): Member {
        val member = memberRepository.findMemberByNameAndProviderId(memberUpdateDTO.name, memberUpdateDTO.providerId)
            .orElseThrow { KorelloNotFoundException() }
        member.changeAuth(memberUpdateDTO.name, memberUpdateDTO.accessToken, memberUpdateDTO.refreshToken)
        return member
    }

    fun createMember(memberCreateDTO: Create): Member {
        return memberRepository.save(memberCreateDTO.toEntity())
    }

    @CacheEvict(value = ["member"], key = "#memberDeleteDTO.providerId + '::' + #memberDeleteDTO.name")
    fun deleteMember(memberDeleteDTO: Delete) {
        return memberRepository.deleteById(memberDeleteDTO.id.toLong())
    }

    @Cacheable(value = ["member"], key = "#providerId + '::' + #name")
    @Transactional(readOnly = true)
    fun findMemberByNameAndProviderId(name: String, providerId: String): InfoResponse {
        val foundMember =
            memberRepository.findMemberByNameAndProviderId(name, providerId).orElseThrow { KorelloNotFoundException() }
        return foundMember.toInfoResponse()
    }
}
