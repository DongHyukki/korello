package com.donghyukki.korello.domain.card.model

import com.donghyukki.korello.domain.board.model.Board
import com.donghyukki.korello.domain.member.model.Member
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Card(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
    @Column
    var name: String,
    @Embedded
    var cardTag: CardTag,
    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    val board: Board?,
    @ManyToMany(fetch = FetchType.LAZY)
    var members: MutableList<Member>

){
    @Column
    @CreationTimestamp
    lateinit var createDate: LocalDateTime

    @Column
    @UpdateTimestamp
    lateinit var updateDate: LocalDateTime

    constructor(name: String, tagValue: String, board: Board) : this(null, name, CardTag(tagValue), board, arrayListOf())
    constructor(name: String, tagValue: String, board: Board, members: List<Member>) : this(null, name, CardTag(tagValue), board, members.toMutableList())

    fun changeName(name: String) {
        this@Card.name = name
    }

    fun changeTag(name: String) {
        this@Card.cardTag = CardTag((name))
    }

    fun changeMembers(members: List<Member>) {
        this@Card.members = members.toMutableList()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Card) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Card(id=$id, name='$name', cardTag=$cardTag, createDate=$createDate, updateDate=$updateDate)"
    }


}