package house.spring.vote.user.domain.model

import house.spring.vote.user.application.repository.UserRepository
import house.spring.vote.common.domain.validation.ValidationResult
import house.spring.vote.common.domain.exception.ConflictException
import house.spring.vote.common.domain.exception.ErrorCode

class User(
    val id: Long? = null,
    var deviceId: String? = null,
    val nickname: String,
) {
    // TODO: 이후 회원 유형 추가시 validation 구현부 도메인에서 분리
    fun validateDeviceIdUnique(userRepository: UserRepository): ValidationResult {
        return if (isDeviceAlreadyRegistered(userRepository)) {
            ValidationResult.Error(ConflictException("${ErrorCode.ALREADY_REGISTERED_DEVICE} ${deviceId!!}"))
        } else {
            ValidationResult.Success
        }
    }

    private fun isDeviceAlreadyRegistered(userRepository: UserRepository): Boolean {
        return userRepository.findByDeviceId(deviceId!!) != null
    }

    companion object {
        fun create(deviceId: String, nickname: String): User {
            return User(deviceId = deviceId, nickname = nickname)
        }
    }
}