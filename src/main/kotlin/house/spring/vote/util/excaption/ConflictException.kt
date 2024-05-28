package house.spring.vote.util.excaption

import org.springframework.http.HttpStatus

class ConflictException(
    message: String,
) : CustomException(HttpStatus.CONFLICT, message) {
}