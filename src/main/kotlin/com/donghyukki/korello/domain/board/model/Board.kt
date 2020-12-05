package com.donghyukki.korello.domain.board.model

import com.donghyukki.korello.domain.card.model.Card
import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator::class, property = "id")
data class Board (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
    @Column
    val name: String,
    @OneToMany(mappedBy = "board")
    val members: MutableList<BoardMembers>,
//    @OneToMany(mappedBy = "board")
//    val cards: MutableList<Card>
){
    @Column
    @CreationTimestamp
    lateinit var createDate: LocalDateTime

    @Column
    @UpdateTimestamp
    lateinit var updateDate: LocalDateTime

    constructor(name: String): this(null, name, arrayListOf())

    fun deleteMember(boardMembers: BoardMembers) {
        members.remove(boardMembers)
    }

    fun addMembers(boardMembers: BoardMembers) {
        members.add(boardMembers)
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