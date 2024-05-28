package house.spring.vote.util.excaption

import org.springframework.http.HttpStatus

class BadRequestException(
    message: String,
) : CustomException(HttpStatus.BAD_REQUEST, message) {
}