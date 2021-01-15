package com.donghyukki.korello.infrastructure.security

import com.donghyukki.korello.domain.member.model.Role
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.session.SessionManagementFilter




@Configuration
class SecurityConfig(
    private val customOAuth2UserService: CustomOAuth2UserService,
): WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity?) {

//        http!!.addFilterBefore(CorsFilter(), SessionManagementFilter::class.java)

        http!!
            .csrf().disable()
            .cors().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                    .antMatchers("/", "/swagger-ui.html").permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name)
            .and()
                .oauth2Login()
                    .userInfoEndpoint()
                        .userService(customOAuth2UserService)
                        .and()
                    .successHandler(OAuth2AuthenticationSuccessHandler())
                    .failureHandler(OAuth2AuthenticationFailureHandler())

    }
}