package com.donghyukki.korello

import com.donghyukki.korello.board.service.BoardService
import com.donghyukki.korello.core.database.H2ServerConfiguration
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@SpringBootTest()
@ActiveProfiles("test")
class KorelloApplicationTests {

    @Test
    fun contextLoads() {

    }

}
