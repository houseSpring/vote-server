package house.spring.vote

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@SpringBootApplication
class VoteApplication

fun main(args: Array<String>) {
    runApplication<VoteApplication>(*args)
}
