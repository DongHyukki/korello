package com.donghyukki.korello.presentation.dto.type

import java.io.Serializable

enum class KorelloEventType(
    val eng_name: String,
    var kor_name: String
) : Serializable {
    BOARD("board", "보드"),
    CARD("card", "카드"),
    MEMBER("member", "멤버"),
    LABEL("label", "라벨"),
    TODO("todo", "할일")
    ;

    companion object {
        private val ulList = listOf(LABEL, TODO)
        private val ulPostposition = "을"
        private val leulPostposition = "를"
    }

    fun toAddedPostName(): String {
        return if (ulList.contains(this)) {
            kor_name + ulPostposition
        } else {
            kor_name + leulPostposition
        }
    }


}
