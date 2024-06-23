package house.spring.vote.common.domain.exception.conflict

import house.spring.vote.common.domain.exception.CustomException
import org.springframework.http.HttpStatus

open class ConflictException(
    message: String,
) : CustomException(HttpStatus.CONFLICT, message) {
}