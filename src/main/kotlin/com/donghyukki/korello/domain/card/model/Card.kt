package com.donghyukki.korello.domain.card.model

import com.donghyukki.korello.domain.board.model.Board
import com.donghyukki.korello.domain.member.model.Member
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Card (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
    @Column
    val name: String,
//    @OneToMany(mappedBy = "board")
//    val members: MutableList<Member>?,
    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    val board: Board?
){

    @Column
    @CreationTimestamp
    lateinit var createDate: LocalDateTime

    @Column
    @UpdateTimestamp
    lateinit var updateDate: LocalDateTime

}