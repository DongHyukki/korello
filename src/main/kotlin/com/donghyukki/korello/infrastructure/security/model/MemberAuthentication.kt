package com.donghyukki.korello.infrastructure.security.model

import com.donghyukki.korello.application.port.AuthenticationFacade
import com.donghyukki.korello.domain.member.model.Role
import com.donghyukki.korello.application.services.member.MemberCrudService
import com.donghyukki.korello.infrastructure.exception.KorelloNotFoundException
import com.donghyukki.korello.infrastructure.security.service.TokenService
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder

@Configuration
class MemberAuthentication(
    private val memberCrudService: MemberCrudService,
    private val tokenService: TokenService
) : AuthenticationFacade {

    companion object {
        private const val CLAIM_PROVIDER_KEY = "providerId"
        private const val CLAIM_NAME_KEY = "name"
    }

    override fun getAuthentication(): Authentication {
        return SecurityContextHolder.getContext().authentication
    }

    override fun getMemberId(): Long {
        val principal = getAuthentication().principal as Map<*, *>
        return principal["id"] as Long
    }

    override fun getMemberName(): String {
        val principal = getAuthentication().principal as Map<*, *>
        return principal["name"] as String
    }

    override fun of(token: String): UsernamePasswordAuthenticationToken {
        val claims = tokenService.getClaim(token)
        val providerId = claims[CLAIM_PROVIDER_KEY].toString()
        val name = claims[CLAIM_NAME_KEY].toString()
        val findMember = memberCrudService.findMemberByNameAndProviderId(name, providerId)
        val role = findMember.role

        return UsernamePasswordAuthenticationToken(
            hashMapOf("name" to name, "id" to findMember.id),
            null,
            Role.toAuthorities(role)
        )
    }

    override fun ofTestAuthentication(): UsernamePasswordAuthenticationToken {
        val findTestMember = memberCrudService.findMemberByNameAndProviderId("swagger-user", "swagger-providerId")
        return UsernamePasswordAuthenticationToken(
            hashMapOf("name" to findTestMember.name, "id" to findTestMember.id),
            null,
            Role.USER.toAuthorities()
        )
    }

    override fun ofAnonymousAuthentication(): UsernamePasswordAuthenticationToken {
        return UsernamePasswordAuthenticationToken(null, null, Role.ANONYMOUS.toAuthorities())
    }

}
