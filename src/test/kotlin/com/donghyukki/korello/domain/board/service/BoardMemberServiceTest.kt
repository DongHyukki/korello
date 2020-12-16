package com.donghyukki.korello.domain.board.service

import com.donghyukki.korello.KorelloApplicationTests
import com.donghyukki.korello.application.services.BoardMemberService
import com.donghyukki.korello.presentation.dto.BoardDTO
import com.donghyukki.korello.presentation.dto.MemberDTO
import com.donghyukki.korello.domain.member.service.MemberCrudService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

internal class BoardMemberServiceTest(
    @Autowired val boardMemberService: BoardMemberService,
    @Autowired val boardCrudService: BoardCrudService,
    @Autowired val memberCrudService: MemberCrudService,
) : KorelloApplicationTests() {

    @Test
    @Transactional
    fun inviteMember() {
        val member = memberCrudService.createMember(MemberDTO.Companion.Create("test member"))
        val board = boardCrudService.createBoard(BoardDTO.Companion.Create("test board"))
        val joinMemberDTO = BoardDTO.Companion.MemberJoin(member.id.toString(), board.id.toString())

        val boardMembers = boardMemberService.inviteMember(joinMemberDTO)

        assertThat(boardMembers.board.name).isEqualTo("test board")
        assertThat(boardMembers.member.name).isEqualTo("test member")

        val findMember = memberCrudService.getMember(member.id!!)
        val findBoard = boardCrudService.getBoard(board.id!!)
        assertThat(findMember.boardJoins).isNotEmpty
        assertThat(findBoard.members).isNotEmpty

    }

    @Test
    @Transactional
    fun exitJoinMember() {
        val member = memberCrudService.createMember(MemberDTO.Companion.Create("test member"))
        val board = boardCrudService.createBoard(BoardDTO.Companion.Create("test board"))
        val joinMemberDTO = BoardDTO.Companion.MemberJoin(member.id.toString(), board.id.toString())
        val exitMemberDTO = BoardDTO.Companion.MemberExit(member.id.toString(), board.id.toString())

        val boardMembers = boardMemberService.inviteMember(joinMemberDTO)

        boardMemberService.exitJoinMember(exitMemberDTO)

        val findMember = memberCrudService.getMember(member.id!!)
        val findBoard = boardCrudService.getBoard(board.id!!)

        assertThat(findMember.boardJoins).isEmpty()
        assertThat(findBoard.members).isEmpty()
    }
}