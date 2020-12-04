package com.donghyukki.korello.core.handler.exeception

import com.donghyukki.korello.core.dto.response.KorelloExceptionResponse
import com.donghyukki.korello.core.exception.KorelloException
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