package xyz.bogeum.config

import org.h2.tools.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("dev")
@Configuration
class H2DbConfig {

    @Bean
    fun h2TcpServerConfig(): Server = Server.createTcpServer().start()
}