package com.donghyukki.korello.infrastructure.security.filter

import com.donghyukki.korello.infrastructure.exception.ResultCode
import com.donghyukki.korello.infrastructure.security.model.MemberAuthentication
import com.donghyukki.korello.infrastructure.web.asset.RequestType
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFilter(
    authenticationManager: AuthenticationManager,
    private val memberAuthentication: MemberAuthentication
) : BasicAuthenticationFilter(authenticationManager) {

    companion object {
        private const val AUTHORIZATION_HEADER_KEY = "Authorization"
        private const val BEARER = "Bearer"
        private const val ANONYMOUS_TOKEN = "ANONYMOUS_TOKEN"
    }

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val token = getTokenFromHeader(request.getHeader(AUTHORIZATION_HEADER_KEY))
        var authentication = memberAuthentication.getAnonymousAuthentication()

        authentication = when (RequestType.parse(request)) {
            RequestType.ACCESS -> {
                try {
                    if (token == ANONYMOUS_TOKEN) authentication else memberAuthentication.of(token)
                } catch (e: ExpiredJwtException) {
                    response.status = ResultCode.EXPIRED_TOKEN.result_code
                    authentication
                } catch (e: Exception) {
                    response.status = ResultCode.ILLEGAL_TOKEN.result_code
                    authentication
                }
            }
            RequestType.REFRESH -> {
                authentication
            }
            RequestType.SWAGGER, RequestType.TEST, RequestType.COCO -> {
                memberAuthentication.getTestAuthentication()
            }
        }

        SecurityContextHolder.getContext().authentication = authentication

        chain.doFilter(request, response)
    }

    private fun getTokenFromHeader(authorizationHeader: String?): String {
        return authorizationHeader?.split(BEARER)?.get(1)?.trim() ?: ANONYMOUS_TOKEN
    }

}

@Bean
fun authenticateTokenFilter(
    authenticationManager: AuthenticationManager,
    memberAuthentication: MemberAuthentication
): AuthenticationFilter {
    return AuthenticationFilter(authenticationManager, memberAuthentication)
}
