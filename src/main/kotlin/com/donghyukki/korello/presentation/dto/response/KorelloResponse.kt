package com.donghyukki.korello.presentation.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class KorelloResponse(
    val result_code: Int,
    val result_message: String,
    val result_time: LocalDateTime,
    val result_body: Any?
) {
    constructor() : this(HttpStatus.OK.value(), "success", LocalDateTime.now(), null)
    constructor(result_body: Any) : this(HttpStatus.OK.value(), "success", LocalDateTime.now(), result_body)
    constructor(result_code: HttpStatus) : this(result_code.value(), "success", LocalDateTime.now(), null)
    constructor(result_code: HttpStatus, result_body: Any) : this(
        result_code.value(),
        "success",
        LocalDateTime.now(),
        result_body
    )
}
