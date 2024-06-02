package house.spring.vote.domain.user.model

import house.spring.vote.domain.user.repository.UserRepository
import house.spring.vote.domain.validation.ValidationResult
import house.spring.vote.util.excaption.ConflictException
import house.spring.vote.util.excaption.ErrorCode

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