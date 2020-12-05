package com.donghyukki.korello.domain.member.repository

import com.donghyukki.korello.domain.member.model.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<Member, Long>