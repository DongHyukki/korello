package com.donghyukki.korello.domain.board.model

import com.donghyukki.korello.domain.member.model.Member
import javax.persistence.*

@Entity
class BoardJoinMembers(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    val board: Board,

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    val member: Member,
) {
    constructor(board: Board, member: Member) : this(null, board, member)

    fun joinBoard() {
        board.addMembers(this)
        member.addBoards(this)
    }

    override fun toString(): String {
        return "BoardJoinMembers(id=$id)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BoardJoinMembers) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

}