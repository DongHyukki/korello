package com.donghyukki.korello.infrastructure.exception

open class KorelloException(
    val error_code: Int,
    val error_message: String,
) : Exception()

class KorelloNotFoundException(error_code: Int = ResultCode.NOT_FOUND_DATA.result_code, error_message: String = ResultCode.NOT_FOUND_DATA.result_message) :
    KorelloException(error_code, error_message)

class KorelloIllegalTokenException(error_code: Int = ResultCode.ILLEGAL_TOKEN.result_code, error_message: String = ResultCode.ILLEGAL_TOKEN.result_message) :
    KorelloException(error_code, error_message)

class KorelloNullTokenException(error_code: Int = ResultCode.NULL_TOKEN.result_code, error_message: String = ResultCode.NULL_TOKEN.result_message) :
    KorelloException(error_code, error_message)

class KorelloExpiredTokenException(error_code: Int = ResultCode.EXPIRED_TOKEN.result_code, error_message: String = ResultCode.EXPIRED_TOKEN.result_message) :
    KorelloException(error_code, error_message)