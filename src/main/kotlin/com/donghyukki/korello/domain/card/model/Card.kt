package com.donghyukki.korello.domain.card.model

import com.donghyukki.korello.domain.board.model.Board
import com.donghyukki.korello.domain.common.BaseEntity
import com.donghyukki.korello.domain.label.model.Label
import com.donghyukki.korello.domain.member.model.Member
import com.donghyukki.korello.domain.todo.model.Todo
import com.donghyukki.korello.presentation.dto.CardDTO
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
    var board: Board?,
    @ManyToMany(fetch = FetchType.LAZY)
    var members: MutableList<Member>,
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "cards")
    var labels: MutableList<Label>,
    @OneToMany(mappedBy = "card", cascade = [CascadeType.ALL], orphanRemoval = true)
    var todos: MutableSet<Todo>,
    @Column
    var dueDate: LocalDateTime? = null,
    @Column
    var displayOrder: Int

) : BaseEntity() {

    constructor(name: String, tagValue: String, board: Board, displayOrder: Int) : this(
        null,
        name,
        CardTag(tagValue),
        board,
        arrayListOf(),
        arrayListOf(),
        mutableSetOf(),
        null,
        displayOrder
    )

    constructor(name: String, tagValue: String, board: Board, members: List<Member>, displayOrder: Int) : this(
        null,
        name,
        CardTag(tagValue),
        board,
        members.toMutableList(),
        arrayListOf(),
        mutableSetOf(),
        null,
        displayOrder
    )

    fun changeName(name: String) {
        this@Card.name = name
    }

    fun changeTag(name: String) {
        this@Card.cardTag = CardTag((name))
    }

    fun changeMembers(members: List<Member>) {
        this@Card.members = members.toMutableList()
    }

    fun changeDueDate(dueDate: LocalDateTime) {
        this@Card.dueDate = dueDate
    }

    fun changeDisplayOrder(displayOrder: Int) {
        this@Card.displayOrder = displayOrder
    }

    fun changeTagAndDisplayOrder(name: String, displayOrder: Int) {
        this@Card.cardTag = CardTag((name))
        this@Card.displayOrder = displayOrder
    }

    fun deleteDueDate() {
        this@Card.dueDate = null
    }

    fun addLabels(labels: List<Label>) {
        this@Card.labels.addAll(labels)
    }

    fun addTodo(todo: Todo) {
        this@Card.todos.add(todo)
    }

    fun deleteLabels(labels: List<Label>) {
        this@Card.labels.removeAll(labels)
    }

    fun clearBoard() {
        this@Card.board = null
    }

    fun clearLabels() {
        this@Card.labels.forEach { label -> label.deleteCard(this) }
        this@Card.labels.clear()
    }

    fun toResponse(): CardDTO.Companion.Response {
        return CardDTO.Companion.Response(
            id!!.toString(),
            name,
            cardTag.tagValue,
            members.map { member -> member.name }.toList(),
            labels.map { label ->
                CardDTO.Companion.LabelResponse(
                    label.id.toString(),
                    label.name,
                    label.color,
                    label.createDate,
                    label.updateDate
                )
            },
            createDate,
            updateDate,
            dueDate,
            displayOrder
        )
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
        return "Card(id=$id, name='$name', cardTag=$cardTag, createDate=$createDate, updateDate=$updateDate, dueDate=$dueDate)"
    }

}