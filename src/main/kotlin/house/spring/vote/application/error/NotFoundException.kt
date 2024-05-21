package house.spring.vote.application.error

import org.springframework.http.HttpStatus

class NotFoundException(
    message: String,
) : CustomException(HttpStatus.NOT_FOUND, message) {
}