package com.donghyukki.korello.domain.todo.model

import com.donghyukki.korello.domain.card.model.Card
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

) {

    @Column
    @CreationTimestamp
    lateinit var createDate: LocalDateTime

    @Column
    @UpdateTimestamp
    lateinit var updateDate: LocalDateTime

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