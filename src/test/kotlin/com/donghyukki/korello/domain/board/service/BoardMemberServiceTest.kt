package com.donghyukki.korello.domain.board.service

import com.donghyukki.korello.KorelloApplicationTests
import com.donghyukki.korello.application.services.BoardCrudService
import com.donghyukki.korello.application.services.BoardMemberService
import com.donghyukki.korello.application.services.MemberCrudService

import org.springframework.beans.factory.annotation.Autowired

internal class BoardMemberServiceTest(
    @Autowired val boardMemberService: BoardMemberService,
    @Autowired val boardCrudService: BoardCrudService,
    @Autowired val memberCrudService: MemberCrudService,
) : KorelloApplicationTests() {

//    @Test
//    @Transactional
//    fun inviteMember() {
//        val member = memberCrudService.createMember(MemberDTO.Companion.Create("test member"))
//        val board = boardCrudService.createBoard(BoardDTO.Companion.Create("test board"))
//        val joinMemberDTO = BoardDTO.Companion.MemberJoin(member.id.toString(), board.id.toString())
//
//        val boardMembers = boardMemberService.inviteMember(joinMemberDTO)
//
//        assertThat(boardMembers.board.name).isEqualTo("test board")
//        assertThat(boardMembers.member.name).isEqualTo("test member")
//
//        val findMember = memberCrudService.getMemberEntity(member.id!!)
//        val findBoard = boardCrudService.getBoardEntity(board.id!!)
//        assertThat(findMember.boardJoins).isNotEmpty
//        assertThat(findBoard.members).isNotEmpty
//
//    }
//
//    @Test
//    @Transactional
//    fun exitJoinMember() {
//        val member = memberCrudService.createMember(MemberDTO.Companion.Create("test member"))
//        val board = boardCrudService.createBoard(BoardDTO.Companion.Create("test board"))
//        val joinMemberDTO = BoardDTO.Companion.MemberJoin(member.id.toString(), board.id.toString())
//        val exitMemberDTO = BoardDTO.Companion.MemberExit(member.id.toString(), board.id.toString())
//
//        val boardMembers = boardMemberService.inviteMember(joinMemberDTO)
//
//        boardMemberService.exitJoinMember(exitMemberDTO)
//
//        val findMember = memberCrudService.getMemberEntity(member.id!!)
//        val findBoard = boardCrudService.getBoardEntity(board.id!!)
//
//        assertThat(findMember.boardJoins).isEmpty()
//        assertThat(findBoard.members).isEmpty()
//    }
}