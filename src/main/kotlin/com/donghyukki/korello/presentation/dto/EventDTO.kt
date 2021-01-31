package com.donghyukki.korello.presentation.dto

class EventDTO(
    private val id: Long,
    private val type: KorelloEventType,
    private val action: KorelloActionType,
    private var memberId: Long?,
    private var memberName: String?,
    private var message: String?,
) {
    constructor(id: Long, type: KorelloEventType, action: KorelloActionType): this(id, type, action,null, null, null)

    fun setMemberId(memberId: Long) {
        this@EventDTO.memberId = memberId
    }

    fun setMemberName(memberName: String) {
        this@EventDTO.memberName = memberName
    }

    fun buildMessage() {
        type.addPostposition()
        this@EventDTO.message = "${type.kor_name} ${action.kor_name} 했습니다."
    }

    enum class KorelloEventType(
        val eng_name: String,
        var kor_name: String
    ) {
        BOARD("board", "보드"),
        CARD("card", "카드"),
        MEMBER("member", "멤버"),
        LABEL("label", "라벨"),
        TODO("todo", "할일");

        companion object {
            private val ulList = listOf(LABEL, TODO)
            private val ulPostposition = "을"
            private val leulPostposition = "를"
        }

        fun addPostposition() {
            if(ulList.contains(this)) {
                this.kor_name += ulPostposition
            } else {
                this.kor_name += leulPostposition
            }
        }


    }

    enum class KorelloActionType(
        val eng_name: String,
        val kor_name: String
    ) {
        CREATE("create", "생성"),
        UPDATE("update", "업데이트"),
        DELETE("delete", "삭제"),
        JOIN("join", "참여"),
        INVITE("invite", "초대"),
        EXIT("exit", "퇴장"),
        ADD("add", "추가");
    }

}