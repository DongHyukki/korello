package com.donghyukki.korello.application.services

import com.donghyukki.korello.domain.board.repository.BoardRepository
import com.donghyukki.korello.presentation.dto.CardDTO.Companion.Create
import com.donghyukki.korello.presentation.dto.CardDTO.Companion.Delete
import com.donghyukki.korello.presentation.dto.CardDTO.Companion.Response
import com.donghyukki.korello.presentation.dto.CardDTO.Companion.UpdateMembers
import com.donghyukki.korello.presentation.dto.CardDTO.Companion.UpdateName
import com.donghyukki.korello.presentation.dto.CardDTO.Companion.UpdateTag
import com.donghyukki.korello.domain.card.model.Card
import com.donghyukki.korello.domain.card.repository.CardRepository
import com.donghyukki.korello.domain.todo.repository.TodoRepository
import com.donghyukki.korello.domain.member.model.Member
import com.donghyukki.korello.presentation.dto.CardDTO
import com.donghyukki.korello.presentation.dto.CardDTO.Companion.LabelResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalStateException


@Service
class BoardCardService(
    private val boardRepository: BoardRepository,
    private val cardRepository: CardRepository,
) {

    @Transactional(readOnly = true)
    fun getAllCardsById(boardId: String): List<Response> {
        val board = boardRepository.findById(boardId.toLong()).orElseThrow()
        return board.cards.map { card ->
            Response(card.id!!.toString()
                , card.name
                , card.cardTag.tagValue
                , card.members.map { member -> member.name }.toList()
                , card.labels.map { label -> LabelResponse(label.id.toString(), label.name, label.color, label.createDate, label.updateDate) }
                , card.createDate
                , card.updateDate)
        }.toList()
    }

    @Transactional
    fun addCardToBoard(boardId: String, cardCreateDTO: Create) {
        val board = boardRepository.findById(boardId.toLong()).orElseThrow()
        val members: List<Member> = arrayListOf()
        if (cardCreateDTO.members != null) {
            members.plus(board.members.filter { boardMembers -> cardCreateDTO.members.contains(boardMembers.member.name) })
        }
        val savedCard = cardRepository.save(Card(cardCreateDTO.name, cardCreateDTO.tagValue, board, members))
        board.addCard(savedCard)
    }

    @Transactional
    fun deleteCardFromBoard(boardId: String, cardDeleteDTO: Delete) {
        val board = boardRepository.findById(boardId.toLong()).orElseThrow()
        val card = cardRepository.findById(cardDeleteDTO.id.toLong()).orElseThrow()
        card.clearLabels()
        board.deleteCard(cardDeleteDTO.id.toLong())
    }

    @Transactional
    fun updateCardTag(boardId: String, cardUpdateTagDTO: UpdateTag) {
        val board = boardRepository.findById(boardId.toLong()).orElseThrow()
        val card = board.cards.firstOrNull { card -> card.id == cardUpdateTagDTO.id.toLong() } ?: throw IllegalStateException()
        card.changeTag(cardUpdateTagDTO.tagValue)
    }

    @Transactional
    fun updateCardName(boardId: String, cardUpdateNameDTO: UpdateName) {
        val board = boardRepository.findById(boardId.toLong()).orElseThrow()
        val card = board.cards.firstOrNull { card -> card.id == cardUpdateNameDTO.id.toLong() } ?: throw IllegalStateException()
        card.changeName(cardUpdateNameDTO.name)
    }

    @Transactional
    fun updateCardMembers(boardId: String, cardUpdateMembersDTO: UpdateMembers) {
        val board = boardRepository.findById(boardId.toLong()).orElseThrow()
        val card = board.cards.firstOrNull { card -> card.id == cardUpdateMembersDTO.id.toLong() } ?: throw IllegalStateException()
        val joinMembers = board.members.map { boardMembers -> boardMembers.member }.toList()
        val updateMembers = joinMembers.filter { member -> cardUpdateMembersDTO.memberNames.contains(member.name) }
        card.changeMembers(updateMembers)
    }

}