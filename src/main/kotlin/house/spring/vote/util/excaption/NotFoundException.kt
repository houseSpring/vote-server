package house.spring.vote.util.excaption

import org.springframework.http.HttpStatus

class NotFoundException(
    message: String,
) : CustomException(HttpStatus.NOT_FOUND, message) {
}