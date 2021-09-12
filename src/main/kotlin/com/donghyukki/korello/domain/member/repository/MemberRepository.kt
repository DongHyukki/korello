package com.donghyukki.korello.domain.member.repository

import com.donghyukki.korello.domain.member.model.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberRepository {
    fun save(member: Member): Member
    fun findById(id: Long): Optional<Member>
    fun findAll(): List<Member>
    fun deleteById(id: Long)
    fun findMemberByNameAndProviderId(name: String, providerId: String): Optional<Member>
}
