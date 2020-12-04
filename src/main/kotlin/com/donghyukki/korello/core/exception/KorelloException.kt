package com.donghyukki.korello.core.exception

data class KorelloException(
    val error_code: Int,
    val error_message: String,
): Exception()

