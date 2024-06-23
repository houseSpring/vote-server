package house.spring.vote.common.domain.exception.internal_server

import house.spring.vote.common.domain.exception.CustomException
import org.springframework.http.HttpStatus

open class InternalServerException(
    message: String = HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase,
) : CustomException(HttpStatus.INTERNAL_SERVER_ERROR, message) {
}