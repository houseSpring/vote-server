package house.spring.vote.common.domain.exception.bad_request

import house.spring.vote.common.domain.exception.ErrorCode

class InvalidPickedPollSizeException(message: String = "") :
    BadRequestException(ErrorCode.INVALID_PICKED_POLL_SIZE + message) {
}