package com.donghyukki.korello.member.domain

import com.donghyukki.korello.board.domain.Board
import javax.persistence.*

@Entity
data class Member (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    @Column
    val name: String,
    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    val board: Board?
)