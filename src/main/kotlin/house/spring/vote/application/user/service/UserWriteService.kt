package house.spring.vote.application.user.service

import house.spring.vote.application.user.dto.command.DeviceJoinCommand
import house.spring.vote.domain.user.model.User
import house.spring.vote.application.user.repository.UserRepository
import house.spring.vote.domain.validation.ValidationResult
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserWriteService(
    private val userRepository: UserRepository,
) {

    @Transactional
    fun join(command: DeviceJoinCommand): User {
        val user = User.create(command.nickname, command.deviceId)

        val validationResult = user.validateDeviceIdUnique(userRepository)
        if (validationResult is ValidationResult.Error) {
            throw validationResult.exception
        }

        return userRepository.save(user)
    }
}