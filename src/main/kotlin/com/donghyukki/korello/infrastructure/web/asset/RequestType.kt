package com.donghyukki.korello.infrastructure.web.asset

import javax.servlet.http.HttpServletRequest

enum class RequestType {
    TEST,
    ACCESS,
    SWAGGER,
    REFRESH,
    COCO,
    ;

    companion object {

        private const val REFRESH_URL = "/oauth2/refresh"

        fun parse(request: HttpServletRequest): RequestType {
            if (request.requestURI == REFRESH_URL) {
                return REFRESH
            }
            if (request.getHeader("User-Agent")?.startsWith("Postman") == true) {
                return TEST
            }
            if (request.getHeader("referer")?.contains("swagger-ui") == true) {
                return SWAGGER
            }
            if (request.getHeader("coco")?.equals("coco") == true) {
                return COCO
            }

            return ACCESS
        }
    }

}
