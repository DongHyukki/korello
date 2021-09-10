package com.donghyukki.korello.domain.card.model

enum class UnLinkType {
    INIT,
    FIRST,
    MIDDLE,
    LAST
    ;

    companion object {

        private const val FIRST_LINK_ID = 0L

        fun parse(cards: List<Card>, linkId: Long, isLast: Boolean): UnLinkType {
            if (cards.isEmpty()) {
                throw IllegalStateException()
            }
            if (cards.count() == 1 && isLast) {
                return INIT
            }
            if (cards.count() > 1 && linkId == FIRST_LINK_ID) {
                return FIRST
            }
            if (cards.count() > 1 || isLast) {
                return LAST
            }

            return MIDDLE
        }

    }
}
