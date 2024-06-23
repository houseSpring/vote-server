package house.spring.vote.common.domain.exception.not_found

import house.spring.vote.common.domain.exception.CustomException
import org.springframework.http.HttpStatus

open class NotFoundException(
    message: String,
) : CustomException(HttpStatus.NOT_FOUND, message) {
}