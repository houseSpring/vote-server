package house.spring.vote.application.user.service

import house.spring.vote.application.user.dto.command.DeviceJoinCommand
import house.spring.vote.domain.user.repository.UserRepository
import house.spring.vote.domain.user.factory.UserFactory
import house.spring.vote.domain.user.model.User
import house.spring.vote.domain.validation.ValidationResult
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserWriteService(
    private val userRepository: UserRepository,
    private val userFactory: UserFactory,
) {

    @Transactional
    fun join(command: DeviceJoinCommand): User {
        val user = userFactory.create(command.nickname, command.deviceId)

        // TODO: 이후 회원 유형 추가시 valdation 구현부 도메인에서 분리
        val validationResult = user.validateDeviceIdUnique(userRepository)
        if (validationResult is ValidationResult.Error) {
            throw validationResult.exception
        }

        return userRepository.save(user)
    }
}