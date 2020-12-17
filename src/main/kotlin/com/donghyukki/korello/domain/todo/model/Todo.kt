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
    val title: String,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CARD_ID")
    val card: Card,
    @Column
    var status: Boolean

) {

    @Column
    @CreationTimestamp
    lateinit var createDate: LocalDateTime

    @Column
    @UpdateTimestamp
    lateinit var updateDate: LocalDateTime

    constructor(title: String, card: Card): this(null, title, card, false)

    fun changeStatus() {
        this@Todo.status = !status
    }
}