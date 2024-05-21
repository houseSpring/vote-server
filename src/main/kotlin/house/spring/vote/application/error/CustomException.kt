package house.spring.vote.application.error

import org.springframework.http.HttpStatus

open class CustomException(val status: HttpStatus, message: String?) : RuntimeException(message) {
}