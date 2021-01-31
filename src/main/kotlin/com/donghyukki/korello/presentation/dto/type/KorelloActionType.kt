package com.donghyukki.korello.presentation.dto.type

import java.io.Serializable

enum class KorelloActionType(
    val eng_name: String,
    val kor_name: String
): Serializable {
    CREATE("create", "생성"),
    UPDATE("update", "업데이트"),
    DELETE("delete", "삭제"),
    JOIN("join", "참여"),
    INVITE("invite", "초대"),
    EXIT("exit", "퇴장"),
    ADD("add", "추가");
}