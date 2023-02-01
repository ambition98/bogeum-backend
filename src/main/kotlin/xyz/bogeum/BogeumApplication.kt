package xyz.bogeum

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@SpringBootApplication
class BogeumApplication

fun main(args: Array<String>) {
    runApplication<BogeumApplication>(*args)
}
