package house.spring.vote.common.domain.exception

import org.springframework.http.HttpStatus

open class CustomException(val status: HttpStatus, message: String?) : RuntimeException(message) {
}