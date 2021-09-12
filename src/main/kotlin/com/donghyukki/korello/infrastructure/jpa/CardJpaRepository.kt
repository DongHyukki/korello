package com.donghyukki.korello.infrastructure.jpa

import com.donghyukki.korello.domain.card.model.Card
import com.donghyukki.korello.domain.card.repository.CardRepository
import org.springframework.data.jpa.repository.JpaRepository

interface CardJpaRepository : CardRepository, JpaRepository<Card, Long>
