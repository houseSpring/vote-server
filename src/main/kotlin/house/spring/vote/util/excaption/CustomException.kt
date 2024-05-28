package house.spring.vote.util.excaption

import org.springframework.http.HttpStatus

open class CustomException(val status: HttpStatus, message: String?) : RuntimeException(message) {
}