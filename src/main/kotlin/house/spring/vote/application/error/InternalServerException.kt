package house.spring.vote.application.error

import org.springframework.http.HttpStatus

class InternalServerException(
    message: String = HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase
) : CustomException(HttpStatus.INTERNAL_SERVER_ERROR, message) {
}