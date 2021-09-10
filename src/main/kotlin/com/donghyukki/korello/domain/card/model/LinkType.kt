package com.donghyukki.korello.domain.card.model

enum class LinkType {
    INIT,
    FIRST,
    APPEND,
    ;
    //1. 제일 첫번째 card의 경우 linkId = 0으로 셋팅..
    //2. 기존에 카드가 있고 제일 첫번째에 추가 된다면 기존에 0번 카드의 link를 해당 card로 설정
    //3. 중간에 카드가 추가된다면 요청온 linkId를 갖고있는 카드의 linkId를 생성된 카드의 Id로, 요청온 linkId는 해당 카드의 Id로
    //4-1. 카드가 삭제된다면 삭제요청 카드가 갖고 있는 linkId를 삭제 요청 카드 id를 갖고있는 카드의 linkId로 변경
    //4-2. 제일 앞 카드가 삭제된다면 해당 카드를 참조하는 linkId를 0으로 셋팅
    //4-3. 제일 뒤 카드가 삭제된다면 그냥 해당 카드만 삭제

    companion object {

        private const val FIRST_LINK_ID = 0L

        fun parse(cards: List<Card>, linkId: Long = FIRST_LINK_ID): LinkType {
            if (cards.isEmpty()) {
                return INIT
            }

            if (cards.isNotEmpty() && linkId == FIRST_LINK_ID) {
                return FIRST
            }

            if (cards.isEmpty() && linkId > FIRST_LINK_ID) {
                return APPEND
            }

            throw IllegalStateException()
        }

    }
}
