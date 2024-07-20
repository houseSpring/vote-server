package house.spring.vote.common.domain.exception.conflict

import house.spring.vote.common.domain.exception.ErrorCode

class AlreadyPickedPostException(message: String) :
    ConflictException(ErrorCode.ALREADY_PICKED_POST + " $message") {
}