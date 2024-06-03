package house.spring.vote.common.domain.exception

import org.springframework.http.HttpStatus

class NotFoundException(
    message: String,
) : CustomException(HttpStatus.NOT_FOUND, message) {
}