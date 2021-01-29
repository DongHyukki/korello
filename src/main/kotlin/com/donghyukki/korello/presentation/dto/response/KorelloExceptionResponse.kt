package com.donghyukki.korello.presentation.dto.response

import com.fasterxml.jackson.databind.ObjectMapper
import java.time.LocalDateTime

data class KorelloExceptionResponse(
    val result_code: Int,
    val result_message: String,
    val result_time: String
) {
    constructor(result_message: String) : this(9999, result_message, LocalDateTime.now().toString())
    constructor(result_code: Int, result_message: String) : this(result_code, result_message, LocalDateTime.now().toString())

    fun toJsonString(): String? {
        return ObjectMapper().writeValueAsString(this)
    }
}
