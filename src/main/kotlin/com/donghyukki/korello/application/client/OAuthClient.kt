package com.donghyukki.korello.application.client

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate

class OAuthClient {

    companion object {
        private const val GRANT_TYPE = "authorization_code"
        private const val CLIENT_ID = "289a13264317080aeb3c1feb3d9d9d0e"
        private const val REDIRECT_URL = "http://localhost:8082/api/v1/auth/authorize"
        private const val AUTH_HOST = "https://kauth.kakao.com/oauth/token"
        private const val CLIENT_SECRET = "obk2faFGMkbgfDSVqPQqp2TxhKGN5ESy"

        fun getUserToken(code: String): Map<*, *>? {
            val restTemplate = RestTemplate()
            val request = HttpEntity(getEntityByParam(code), getHttpHeader())

            return restTemplate.postForObject(AUTH_HOST, request, Map::class.java)
        }

        private fun getEntityByParam(code: String): LinkedMultiValueMap<String, String> {
            val map = LinkedMultiValueMap<String, String>()
            map.add("grant_type", GRANT_TYPE)
            map.add("client_id", CLIENT_ID)
            map.add("redirect_uri", REDIRECT_URL)
            map.add("code", code)
            map.add("client_secret", CLIENT_SECRET)
            return map
        }

        private fun getHttpHeader(): HttpHeaders {
            val headers = HttpHeaders()
            headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
            return headers
        }

    }
}