package com.donghyukki.korello.infrastructure.security.config

import com.donghyukki.korello.domain.member.model.Role
import com.donghyukki.korello.infrastructure.security.filter.CorsFilter
import com.donghyukki.korello.infrastructure.security.filter.authenticateTokenFilter
import com.donghyukki.korello.infrastructure.security.handler.OAuth2AccessDeniedHandler
import com.donghyukki.korello.infrastructure.security.handler.OAuth2AuthenticationFailureHandler
import com.donghyukki.korello.infrastructure.security.handler.OAuth2AuthenticationSuccessHandler
import com.donghyukki.korello.infrastructure.security.model.MemberAuthentication
import com.donghyukki.korello.infrastructure.security.service.OAuthUserService
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.session.SessionManagementFilter


@Configuration
class SecurityConfig(
    private val OAuthUserService: OAuthUserService,
    private val oAuth2AccessDeniedHandler: OAuth2AccessDeniedHandler,
    private val memberAuthentication: MemberAuthentication
): WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {

        http
            .addFilterBefore(CorsFilter(), SessionManagementFilter::class.java)
            .addFilterBefore(authenticateTokenFilter(authenticationManagerBean(), memberAuthentication), UsernamePasswordAuthenticationFilter::class.java)

        http
            .csrf().disable()
            .cors().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                    .antMatchers("/", "/swagger-ui.html", "/health_check.html", "/oauth2/authorization/**", "/oauth2/refresh").permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name)
            .and()
                .exceptionHandling()
                .accessDeniedHandler(oAuth2AccessDeniedHandler)
            .and()
                .oauth2Login()
                    .userInfoEndpoint()
                        .userService(OAuthUserService)
                        .and()
                    .successHandler(OAuth2AuthenticationSuccessHandler())
                    .failureHandler(OAuth2AuthenticationFailureHandler())
    }

    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }
}
