package com.donghyukki.korello.domain.member.repository

import com.donghyukki.korello.domain.member.model.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberRepository : JpaRepository<Member, Long> {
    fun findMemberByNameAndProviderId(name: String, providerId: String): Optional<Member>
}