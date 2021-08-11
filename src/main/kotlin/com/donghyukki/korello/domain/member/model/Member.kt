package com.donghyukki.korello.domain.member.model

import com.donghyukki.korello.domain.board.model.BoardJoinMembers
import com.donghyukki.korello.domain.card.model.Card
import com.donghyukki.korello.domain.common.BaseEntity
import com.donghyukki.korello.presentation.dto.BoardDTO.Companion.Response
import com.donghyukki.korello.presentation.dto.MemberDTO
import com.donghyukki.korello.presentation.dto.MemberDTO.Companion.InfoResponse
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import javax.persistence.*

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long?,
    @Column
    var name: String,
    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    @OneToMany(mappedBy = "member")
    val boardJoins: MutableList<BoardJoinMembers>,
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "members")
    val cards: MutableList<Card>,
    @Column
    val role: String,
    @Column
    val providerId: String,
    @Column
    val registrationId: String,
    @Column
    var accessToken: String,
    @Column
    var refreshToken: String
) : BaseEntity() {
    constructor(
        name: String,
        role: Role,
        providerId: String,
        registrationId: String,
        accessToken: String,
        refreshToken: String
    ) : this(
        null,
        name,
        arrayListOf(),
        arrayListOf(),
        role.value,
        providerId,
        registrationId,
        accessToken,
        refreshToken
    )

    fun addBoards(boardJoinMembers: BoardJoinMembers) {
        boardJoins.add(boardJoinMembers)
    }

    fun exitBoard(joinBoardJoinMembers: BoardJoinMembers) {
        boardJoins.remove(joinBoardJoinMembers)
    }

    fun getJoinBoards(): List<Response> {
        return boardJoins.map { boardJoinMembers ->
            Response(
                boardJoinMembers.board.id.toString(),
                boardJoinMembers.board.name,
                boardJoinMembers.board.members.map { boardJoinMembers -> boardJoinMembers.member.name }
                    .toList(),
                boardJoinMembers.board.createDate,
                boardJoinMembers.board.updateDate
            )
        }.toList()
    }

    fun changeAuth(name: String, accessToken: String, refreshToken: String) {
        this@Member.name = name
        this@Member.accessToken = accessToken
        this@Member.refreshToken = refreshToken
    }

    fun changeName(name: String) {
        this@Member.name = name
    }

    fun toInfoResponse(): InfoResponse {
        return InfoResponse(id!!, name, providerId, role)
    }

    override fun toString(): String {
        return "Member(id=$id, name='$name')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Member) return false

        if (id != other.id) return false
        if (providerId != other.providerId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + providerId.hashCode()
        return result
    }
}
