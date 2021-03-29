package com.donghyukki.korello.infrastructure.aop

import com.donghyukki.korello.infrastructure.exception.KorelloException
import com.donghyukki.korello.presentation.dto.response.KorelloExceptionResponse
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalControllerAdvice {

    @ExceptionHandler(KorelloException::class)
    fun catchExceptionAndResponse(e: KorelloException): KorelloExceptionResponse {
        return KorelloExceptionResponse(e.error_code, e.error_message)
    }

    @ExceptionHandler(Exception::class)
    fun catchExceptionAndResponse(e: Exception): KorelloExceptionResponse {
        return KorelloExceptionResponse(e.toString())
    }
}