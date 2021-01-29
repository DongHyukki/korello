package com.donghyukki.korello.infrastructure.security.filter

import com.donghyukki.korello.domain.member.service.MemberCrudService
import com.donghyukki.korello.infrastructure.exception.KorelloNotFoundException
import com.donghyukki.korello.infrastructure.security.config.JwtConfig
import com.donghyukki.korello.presentation.dto.response.KorelloExceptionResponse
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer.UserDetailsBuilder
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFilter(
    authenticationManager: AuthenticationManager,
    private val jwtConfig: JwtConfig,
    private val memberCrudService: MemberCrudService
) :
    BasicAuthenticationFilter(authenticationManager) {

    private val AUTHORIZATION_HEADER_KEY = "Authorization";
    private val BEARER = "Bearer"
    private val ANONYMOUS = "ROLE_ANONYMOUS"
    private val CLAIM_PROVIDER_KEY = "providerId"
    private val CLAIM_NAME_KEY = "name"

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val token = getTokenFromHeader(request.getHeader(AUTHORIZATION_HEADER_KEY))
        var authentication = UsernamePasswordAuthenticationToken(null, null, roleToAuthorities(ANONYMOUS))

        if (token != ANONYMOUS) {
            try {
                val claims = jwtConfig.getClaim(token)
                authentication = claimsToAuthentication(claims)
            } catch (e: ExpiredJwtException) {
                response.writer.write(KorelloExceptionResponse(9998, "Token Expired").toJsonString()!!)
            } catch (e: Exception) {
                response.writer.write(KorelloExceptionResponse(e.cause.toString()).toJsonString()!!)
            }
        }

        SecurityContextHolder.getContext().authentication = authentication

        chain.doFilter(request, response);
    }

    private fun getTokenFromHeader(authorizationHeader: String?): String {
        return authorizationHeader?.split(BEARER)?.get(1)?.trim() ?: ANONYMOUS
    }

    private fun claimsToAuthentication(claims: Claims): UsernamePasswordAuthenticationToken {
        val providerId = claims[CLAIM_PROVIDER_KEY].toString()
        val name = claims[CLAIM_NAME_KEY].toString()
        val findMember = memberCrudService.findMemberByNameAndProviderId(name, providerId).orElseThrow { KorelloNotFoundException() }
        val role = findMember.role

        return UsernamePasswordAuthenticationToken(findMember.id!!, null, roleToAuthorities(role))
    }

    private fun roleToAuthorities(role: String): MutableList<SimpleGrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(role))
    }
}

@Bean
fun authenticateTokenFilter(
    authenticationManager: AuthenticationManager,
    jwtConfig: JwtConfig,
    memberCrudService: MemberCrudService
): AuthenticationFilter {
    return AuthenticationFilter(authenticationManager, jwtConfig, memberCrudService)
}