package house.spring.vote.application.error

import org.springframework.http.HttpStatus

class BadRequestException(
    message: String,
) : CustomException(HttpStatus.BAD_REQUEST, message) {
}