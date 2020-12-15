package com.donghyukki.korello.infrastructure.exception

data class KorelloException(
    val error_code: Int,
    val error_message: String,
): Exception()

