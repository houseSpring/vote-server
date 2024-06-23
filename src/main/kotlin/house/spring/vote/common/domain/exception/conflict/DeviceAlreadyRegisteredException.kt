package house.spring.vote.common.domain.exception.conflict

import house.spring.vote.common.domain.exception.ErrorCode

class DeviceAlreadyRegisteredException(message: String) :
    ConflictException(ErrorCode.ALREADY_REGISTERED_DEVICE + " $message") {
}