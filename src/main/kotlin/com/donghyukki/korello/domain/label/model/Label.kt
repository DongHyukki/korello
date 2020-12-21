package com.donghyukki.korello.domain.label.model

import com.donghyukki.korello.domain.board.model.Board
import com.donghyukki.korello.domain.card.model.Card
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Label(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    val board: Board,
    @Column
    var name: String,
    @Column
    var color: String,
    @ManyToMany(fetch = FetchType.LAZY)
    val cards: MutableList<Card>,
) {
    @Column
    @CreationTimestamp
    lateinit var createDate: LocalDateTime

    @Column
    @UpdateTimestamp
    lateinit var updateDate: LocalDateTime

    constructor(board: Board, name: String, color: String): this(null, board, name, color, arrayListOf())

    fun addCard(card: Card) {
        this@Label.cards.add(card)
    }

    fun deleteCard(card: Card) {
        this@Label.cards.remove(card)
    }

    fun changeName(name: String) {
        this@Label.name = name
    }

    fun changeColor(color: String) {
        this@Label.color = color
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Label) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }


}