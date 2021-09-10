package com.donghyukki.korello.domain.board.model

import com.donghyukki.korello.domain.card.model.Card
import com.donghyukki.korello.domain.common.BaseEntity
import javax.persistence.*

@Entity
class Board(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
    @Column
    val name: String,
    @OneToMany(mappedBy = "board", cascade = [CascadeType.ALL], orphanRemoval = true)
    val members: MutableList<BoardJoinMembers>,
    @OneToMany(mappedBy = "board", cascade = [CascadeType.ALL], orphanRemoval = true)
    var cards: MutableSet<Card>,
) : BaseEntity() {

    constructor(name: String) : this(null, name, arrayListOf(), hashSetOf())

    fun deleteMember(boardJoinMembers: BoardJoinMembers) {
        members.remove(boardJoinMembers)
    }

    fun addMembers(boardJoinMembers: BoardJoinMembers) {
        members.add(boardJoinMembers)
    }

    fun addCard(card: Card) {
        cards.add(card)
    }

    fun deleteCard(cardId: Long) {
        this@Board.cards.removeIf { card -> card.id == cardId }
    }

    fun clearCard() {
        this@Board.cards.forEach { card ->
            run {
                card.clearLabels()
                card.clearBoard()
            }
        }
    }

    override fun toString(): String {
        return "Board(id=$id, name='$name')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Board) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }


}
