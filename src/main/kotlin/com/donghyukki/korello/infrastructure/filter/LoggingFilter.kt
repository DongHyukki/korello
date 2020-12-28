package com.donghyukki.korello.infrastructure.filter

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.LoggerFactory
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class LoggingFilter: OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val logger = LoggerFactory.getLogger("HTTP_LOGGER")
        var requestData: String? = null
        var responseData: String? = null

        val mapper = ObjectMapper()
        val requestCacheWrapperObject = ContentCachingRequestWrapper(request)
        val responseCacheWrapperObject = ContentCachingResponseWrapper(response)

        filterChain.doFilter(requestCacheWrapperObject, responseCacheWrapperObject)

        val requestContents = requestCacheWrapperObject.contentAsByteArray

        if(requestContents.isNotEmpty()) {
            requestData = mapper.readValue<Any>(requestContents).toString()
        }

        val responseContents = responseCacheWrapperObject.contentAsByteArray

        if(responseContents.isNotEmpty()) {
            responseData = mapper.readValue<Any>(responseCacheWrapperObject.contentAsByteArray).toString()
        }

        val logObject = LogObject(
            requestCacheWrapperObject.requestURL.toString(),
            requestCacheWrapperObject.method,
            requestData,
            responseData
        )

        logger.info(logObject.toString())

        responseCacheWrapperObject.copyBodyToResponse()
    }
}

@JsonInclude(JsonInclude.Include.NON_NULL)
data class LogObject(
    val http_url: String,
    val http_method: String,
    var request_body: String?,
    val response_body: String?
) {
    override fun toString(): String {
        return "(http_url='$http_url', http_method='$http_method', request_body='$request_body', response_body='$response_body')"
    }
}
