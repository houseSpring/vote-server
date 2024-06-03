package house.spring.vote.common.domain.exception

import org.springframework.http.HttpStatus

class ConflictException(
    message: String,
) : CustomException(HttpStatus.CONFLICT, message) {
}