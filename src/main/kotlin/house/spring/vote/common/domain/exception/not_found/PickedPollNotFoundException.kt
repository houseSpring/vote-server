package house.spring.vote.common.domain.exception.not_found

import house.spring.vote.common.domain.exception.ErrorCode

class PickedPollNotFoundException(message: String) : NotFoundException(ErrorCode.POLL_NOT_FOUND + " $message") {
}