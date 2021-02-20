package com.donghyukki.korello.domain.todo.model

import com.donghyukki.korello.domain.card.model.Card
import com.donghyukki.korello.domain.common.BaseEntity
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Todo(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
    @Column
    var title: String,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CARD_ID")
    var card: Card?,
    @Column
    var status: Boolean

) : BaseEntity() {
    constructor(title: String, card: Card) : this(null, title, card, false)

    fun changeStatus() {
        this@Todo.status = !status
    }

    fun changeTitle(title: String) {
        this@Todo.title = title
    }

    fun clearCard() {
        this@Todo.card = null
    }
}