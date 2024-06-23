package house.spring.vote.common.domain.exception.bad_request

import house.spring.vote.common.domain.exception.CustomException
import org.springframework.http.HttpStatus

open class BadRequestException(
    message: String,
) : CustomException(HttpStatus.BAD_REQUEST, message) {
}