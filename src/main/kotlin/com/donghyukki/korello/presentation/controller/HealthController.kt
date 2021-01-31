package com.donghyukki.korello.presentation.controller

import com.donghyukki.korello.presentation.dto.response.KorelloResponse
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthController {
    @Operation(summary = "HEALTH CHECK", description = "HEALTH CHECK")
    @GetMapping("/health_check.html")
    fun healthCheck(): KorelloResponse {
        return KorelloResponse()
    }
}
