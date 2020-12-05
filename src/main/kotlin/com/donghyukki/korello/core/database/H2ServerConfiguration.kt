package com.donghyukki.korello.core.database

import com.zaxxer.hikari.HikariDataSource
import org.h2.tools.Server
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
class H2ServerConfiguration {

    @Bean
    @ConfigurationProperties("spring.datasource.hikari")
    fun dataSource(): HikariDataSource = run {
        val server: Server = h2TcpServer()
        if(server.isRunning(true)) {
            server.stop().apply {
                server.start()
            }
        }
        return HikariDataSource()
    }

    fun h2TcpServer(): Server = Server.createTcpServer(
        "-tcp",
        "-tcpAllowOthers",
        "-ifNotExists",
        "-tcpPort", "9093" + "")
}