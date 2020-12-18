package com.donghyukki.korello.infrastructure.filter

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

        val mapper = ObjectMapper()
        val requestCacheWrapperObject = ContentCachingRequestWrapper(request)
        val responseCacheWrapperObject = ContentCachingResponseWrapper(response)

        filterChain.doFilter(requestCacheWrapperObject, responseCacheWrapperObject)

        val requestContents = requestCacheWrapperObject.contentAsByteArray

        if(requestContents.isNotEmpty()) {
            val requestData = mapper.readValue<Any>(requestContents)
            logger.info(requestData.toString())
        }

        val responseData = mapper.readValue<Any>(responseCacheWrapperObject.contentAsByteArray)
        logger.info(responseData.toString())

        responseCacheWrapperObject.copyBodyToResponse()
    }
}