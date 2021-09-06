package com.donghyukki.korello.infrastructure.swagger

import com.donghyukki.korello.domain.member.model.Role
import com.donghyukki.korello.application.services.member.MemberCrudService
import com.donghyukki.korello.domain.member.repository.MemberRepository
import com.donghyukki.korello.presentation.dto.MemberDTO
import org.springdoc.core.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct


@Configuration
class SwaggerConfig(
    private val memberRepository: MemberRepository
) {
    @PostConstruct
    fun setUpSwaggerUser() {
        val swaggerMember = memberRepository.findMemberByNameAndProviderId("swagger-user", "swagger-providerId")
        if (!swaggerMember.isPresent) {
            memberRepository.save(
                MemberDTO.Companion.Create(
                    name = "swagger-user",
                    role = Role.USER,
                    providerId = "swagger-providerId",
                    registrationId = "swagger-user",
                    accessToken = "swagger-token",
                    refreshToken = "swagger-token"
                ).toEntity()
            )
        }
    }

    @Bean
    fun boardApi(): GroupedOpenApi? {
        return GroupedOpenApi.builder()
            .group("board")
            .packagesToScan("com.donghyukki.korello.presentation.controller.board")
            .build()
    }

    @Bean
    fun memberApi(): GroupedOpenApi? {
        return GroupedOpenApi.builder()
            .group("member")
            .packagesToScan("com.donghyukki.korello.presentation.controller.member")
            .build()
    }

    @Bean
    fun authApi(): GroupedOpenApi? {
        return GroupedOpenApi.builder()
            .group("auth")
            .packagesToScan("com.donghyukki.korello.presentation.controller.auth")
            .build()
    }

}
