package com.donghyukki.korello.infrastructure.jpa

import com.donghyukki.korello.domain.member.model.Member
import com.donghyukki.korello.domain.member.repository.MemberRepository
import org.springframework.data.jpa.repository.JpaRepository

interface MemberJpaRepository : MemberRepository, JpaRepository<Member, Long>
