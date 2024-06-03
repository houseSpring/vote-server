package house.spring.vote.common.domain.exception

import org.springframework.http.HttpStatus

class InternalServerException(
    message: String = HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase
) : CustomException(HttpStatus.INTERNAL_SERVER_ERROR, message) {
}