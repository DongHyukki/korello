package com.donghyukki.korello.infrastructure.security.handler

import com.donghyukki.korello.infrastructure.exception.ResultCode
import com.donghyukki.korello.infrastructure.exception.ResultCode.Companion.COMMON_ERROR
import com.donghyukki.korello.infrastructure.exception.ResultCode.Companion.EXPIRED_TOKEN
import com.donghyukki.korello.infrastructure.exception.ResultCode.Companion.ILLEGAL_TOKEN
import com.donghyukki.korello.infrastructure.exception.ResultCode.Companion.NULL_TOKEN
import com.donghyukki.korello.presentation.dto.response.KorelloExceptionResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class OAuth2AccessDeniedHandler: AccessDeniedHandler {

    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        accessDeniedException: AccessDeniedException?
    ) {
        response?.contentType = MediaType.APPLICATION_JSON_VALUE

        when(response?.status) {
            EXPIRED_TOKEN.result_code -> response.writer.write(KorelloExceptionResponse(EXPIRED_TOKEN.result_code, EXPIRED_TOKEN.result_message).toJsonString()!!)
            ILLEGAL_TOKEN.result_code -> response.writer.write(KorelloExceptionResponse(ILLEGAL_TOKEN.result_code, ILLEGAL_TOKEN.result_message).toJsonString()!!)
            else -> response?.writer?.write(KorelloExceptionResponse(NULL_TOKEN.result_code, NULL_TOKEN.result_message).toJsonString()!!)
        }

        response?.status = HttpStatus.UNAUTHORIZED.value()
    }

}