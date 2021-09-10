package com.donghyukki.korello.domain.card.service

import com.donghyukki.korello.domain.card.model.Card
import com.donghyukki.korello.domain.card.model.LinkType
import com.donghyukki.korello.domain.card.model.UnLinkType
import com.donghyukki.korello.domain.card.repository.CardRepository
import org.springframework.stereotype.Component
import java.lang.IllegalStateException

@Component
class CardDomainService(
    private val cardRepository: CardRepository
) {

    fun link(card: Card, linkId: Long) {
        val cards = cardRepository.findAllByCardTagAndBoardId(card.cardTag, card.board?.id!!)
        when(LinkType.parse(cards, linkId)) {
            LinkType.INIT -> card.initLinkId()
            LinkType.FIRST -> { parseFirstCard(cards).changeLinkId(card.id!!); card.initLinkId() }
            LinkType.APPEND -> { parseCardByLinkId(cards, linkId).changeLinkId(card.id!!); card.changeLinkId(linkId) }
        }
    }

    fun unLink(card: Card, isLast: Boolean) {
        val cards = cardRepository.findAllByCardTagAndBoardId(card.cardTag, card.board?.id!!)
        when(UnLinkType.parse(cards, card.linkId, isLast)) {
            UnLinkType.FIRST -> { parseCardByLinkId(cards, card.linkId).initLinkId() }
            UnLinkType.MIDDLE -> {
                val linkCard = parseCardByLinkId(cards, card.linkId)
                parseCardByLinkId(cards, card.id!!).changeLinkId(linkCard.id!!)
            }
            UnLinkType.INIT, UnLinkType.LAST -> { Unit }
        }
    }

    private fun parseFirstCard(cards: List<Card>): Card {
        return cards.firstOrNull { it.linkId == 0L } ?: throw IllegalStateException()
    }

    private fun parseCardByLinkId(cards: List<Card>, linkId: Long? = 0L): Card {
        return cards.firstOrNull { it.id!! == linkId } ?: throw IllegalStateException()
    }


}
