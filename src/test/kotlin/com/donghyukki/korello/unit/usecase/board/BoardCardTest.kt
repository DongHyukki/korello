//package com.donghyukki.korello.unit.usecase.board
//
//import com.donghyukki.korello.application.services.board.BoardCardService
//import com.donghyukki.korello.domain.board.model.Board
//import com.donghyukki.korello.domain.board.repository.BoardRepository
//import com.donghyukki.korello.domain.card.model.Card
//import com.donghyukki.korello.domain.card.model.CardTag
//import com.donghyukki.korello.domain.card.repository.CardRepository
//import com.donghyukki.korello.domain.label.model.Label
//import com.donghyukki.korello.domain.member.model.Member
//import com.donghyukki.korello.domain.member.model.Role
//import com.donghyukki.korello.unit.TestEventPublisher
//import com.donghyukki.korello.unit.UnitTestRunner
//import io.mockk.every
//import io.mockk.impl.annotations.MockK
//import io.mockk.mockk
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.DisplayName
//import org.junit.jupiter.api.Test
//import java.time.LocalDateTime
//import java.util.Optional
//
//internal class BoardCardTest : UnitTestRunner() {
//
//    @MockK
//    lateinit var boardRepository: BoardRepository
//
//    @MockK
//    lateinit var cardRepository: CardRepository
//
//    private val korelloEventPublisher = TestEventPublisher();
//
//    lateinit var boardCardService: BoardCardService
//
//    @BeforeEach
//    fun setUp() {
//        boardCardService = BoardCardService(boardRepository, cardRepository, korelloEventPublisher)
//    }
//
//    @Test
//    @DisplayName("특정 보드의 모든 카드를 조회한다")
//    fun `특정 보드의 모든 카드를 조회한다`() {
//        val board = Board("TestBoard")
//        val testId = "1"
//        val mockCard = getMockCard(board)
//
//        board.addCard(mockCard)
//
//        every { boardRepository.findById(any()) } returns Optional.of(board)
//
//        val cards = boardCardService.getAllCardsById(testId)
//
//        println("cards = ${cards}")
//    }
//
//    private fun getMockCard(mockBoard: Board): Card {
//        return mockk(relaxed = true) {
//            every<Long?> { id } returns 1L
//            every<String> { name } returns "테스트 카드"
//            every<Board?> { board } returns mockBoard
//            every<CardTag> { cardTag } returns CardTag("테스트 태그")
//            every<Int> { linkId } returns 1
//            every<MutableList<Member>> { members } returns mutableListOf(
//                Member(
//                    "테스트 멤버",
//                    Role.USER,
//                    "test",
//                    "test",
//                    "test",
//                    "test"
//                )
//            )
//            every<LocalDateTime> { createDate } returns LocalDateTime.now()
//            every<LocalDateTime> { updateDate } returns LocalDateTime.now()
//            every<LocalDateTime?> { dueDate } returns LocalDateTime.now()
//            every<MutableList<Label>> { labels } returns mutableListOf(
//                mockk {
//                    every<Long?> { id } returns 1
//                    every<String> { name } returns "테스트 라벨"
//                    every<String> { color } returns "######"
//                    every<LocalDateTime> { createDate } returns LocalDateTime.now()
//                    every<LocalDateTime> { updateDate } returns LocalDateTime.now()
//                }
//            )
//        }
//    }
//}
