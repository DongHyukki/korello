package com.donghyukki.korello.domain.member.service

import com.donghyukki.korello.presentation.dto.MemberDTO.Companion.Create
import com.donghyukki.korello.presentation.dto.MemberDTO.Companion.Delete
import com.donghyukki.korello.domain.member.model.Member
import com.donghyukki.korello.domain.member.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberCrudService(
    val memberRepository: MemberRepository
) {
    fun getAllMembers(): MutableList<Member> {
        return memberRepository.findAll()
    }

    fun getMember(memberId: Long): Member {
        return memberRepository.findById(memberId).orElseThrow {
            IllegalArgumentException("Member Not Existed")
        }
    }

    fun createMember(memberCreateDTO: Create): Member {
        return memberRepository.save(memberCreateDTO.toEntity())
    }

    fun deleteMember(memberDeleteDTO: Delete) {
        return memberRepository.deleteById(memberDeleteDTO.id.toLong())
    }
}