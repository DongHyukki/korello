package com.donghyukki.korello.domain.member.model

import com.donghyukki.korello.domain.board.model.BoardMembers
import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Member (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long?,
    @Column
    val name: String,
    @OneToMany(mappedBy = "member")
    val boards: MutableList<BoardMembers>,
) {
    @Column
    @CreationTimestamp
    lateinit var createDate: LocalDateTime

    @Column
    @UpdateTimestamp
    lateinit var updateDate: LocalDateTime

    constructor(name: String): this(null, name, arrayListOf())

    fun addBoards(boardMembers: BoardMembers) {
        boards.add(boardMembers)
    }

    override fun toString(): String {
        return "Member(id=$id, name='$name')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Member) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    fun exitBoard(joinBoardMembers: BoardMembers) {
        boards.remove(joinBoardMembers)
    }


}