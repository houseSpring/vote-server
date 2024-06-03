package house.spring.vote.common.domain.exception

import org.springframework.http.HttpStatus

class BadRequestException(
    message: String,
) : CustomException(HttpStatus.BAD_REQUEST, message) {
}