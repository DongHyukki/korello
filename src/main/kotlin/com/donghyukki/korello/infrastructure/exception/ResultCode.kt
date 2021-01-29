package com.donghyukki.korello.infrastructure.exception

class ResultCode(
    val result_code: Int,
    val result_message: String
) {
    companion object {
        val COMMON_ERROR = ResultCode(500_000, "Korello Error")
        val NOT_FOUND_DATA = ResultCode(500_001, "Not Found Data")
        val EXPIRED_TOKEN = ResultCode(401_001, "Token Expired")
        val ILLEGAL_TOKEN = ResultCode(401_002, "Illegal Token")
        val NULL_TOKEN = ResultCode(401_003, "Authorization Token Required")
    }
}