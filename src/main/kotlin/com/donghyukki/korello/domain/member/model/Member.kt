package com.donghyukki.korello.domain.member.model

import com.donghyukki.korello.domain.board.model.BoardJoinMembers
import com.donghyukki.korello.domain.card.model.Card
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
    val boardJoins: MutableList<BoardJoinMembers>,
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "members")
    val cards: MutableList<Card>
) {
    @Column
    @CreationTimestamp
    lateinit var createDate: LocalDateTime

    @Column
    @UpdateTimestamp
    lateinit var updateDate: LocalDateTime

    constructor(name: String): this(null, name, arrayListOf(), arrayListOf())

    fun addBoards(boardJoinMembers: BoardJoinMembers) {
        boardJoins.add(boardJoinMembers)
    }

    fun exitBoard(joinBoardJoinMembers: BoardJoinMembers) {
        boardJoins.remove(joinBoardJoinMembers)
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




}