package com.donghyukki.korello.infrastructure.web

import com.donghyukki.korello.infrastructure.filter.LoggingFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("POST", "GET", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(false)
            .maxAge(3000)
    }
}

@Configuration
class FilterRegistration {
    @Bean
    fun getFilterRegistrationBean(): FilterRegistrationBean<LoggingFilter> {
        val filter = FilterRegistrationBean(LoggingFilter())
        filter.addUrlPatterns("/api/v1/*")
        return filter
    }
}