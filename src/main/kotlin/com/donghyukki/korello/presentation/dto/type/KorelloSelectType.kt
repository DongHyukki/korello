package com.donghyukki.korello.presentation.dto.type

import java.io.Serializable

enum class KorelloSelectType(
    val eng_name: String,
    var kor_name: String
) : Serializable {
    BOARD("board", "보드"),
    CARD("card", "카드"),
}
