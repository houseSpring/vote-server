package house.spring.vote.application.error

import org.springframework.http.HttpStatus

class ConflictException(
    message: String,
) : CustomException(HttpStatus.CONFLICT, message) {
}