package com.donghyukki.korello.infrastructure.exception

open class KorelloException(
    val error_code: Int,
    val error_message: String,
) : Exception()

class KorelloNotFoundException(error_code: Int = 500_001, error_message: String = "Not Found Data") :
    KorelloException(error_code, error_message)

