package house.spring.vote.common.domain.exception.not_found

import house.spring.vote.common.domain.exception.ErrorCode

class PostNotFoundException(message: String) : NotFoundException(ErrorCode.POST_NOT_FOUND + " $message") {
}