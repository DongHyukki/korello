package com.donghyukki.korello.infrastructure.web.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

@Configuration
@EnableAsync
class AsyncThreadConfig {
    @Bean
    fun asyncThreadTaskExecutor(): Executor {
        return ThreadPoolTaskExecutor().apply {
            this.corePoolSize = 10
            this.maxPoolSize = 10
            this.setQueueCapacity(10)
            this.setThreadNamePrefix("Async-Executor")
        }
    }
}