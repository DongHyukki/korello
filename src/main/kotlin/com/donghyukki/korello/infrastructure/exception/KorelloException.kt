package com.donghyukki.korello.infrastructure.exception

open class KorelloException(
    val error_code: Int,
    val error_message: String,
) : Exception()

class KorelloNotFoundException(error_code: Int = ResultCode.NOT_FOUND_DATA.result_code, error_message: String = ResultCode.NOT_FOUND_DATA.result_message) :
    KorelloException(error_code, error_message)