package com.donghyukki.korello.domain.card.model

import javax.persistence.Embeddable

@Embeddable
class CardTag(
    val tagValue: String
)