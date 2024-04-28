package house.spring.vote

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class VoteApplication

fun main(args: Array<String>) {
	runApplication<VoteApplication>(*args)
}
