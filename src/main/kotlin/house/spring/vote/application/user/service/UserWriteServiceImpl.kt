package house.spring.vote.application.user.service

import house.spring.vote.application.user.dto.command.JoinCommand
import house.spring.vote.application.user.dto.command.LoginCommand
import house.spring.vote.application.user.port.UserMapper
import house.spring.vote.application.user.port.UserWriteService
import house.spring.vote.domain.user.factory.UserFactory
import house.spring.vote.domain.user.repository.UserRepository
import house.spring.vote.domain.validation.ValidationResult
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class UserWriteServiceImpl(
    private val userRepository: UserRepository,
    private val userFactory: UserFactory,
    private val userMapper: UserMapper
) :
    UserWriteService {

    @Transactional
    override fun join(command: JoinCommand): Long {
        val user = userFactory.create(command.nickname, command.deviceId)

        val validationResult = user.validateDeviceIdUnique(userRepository)
        if (validationResult is ValidationResult.Error) {
            throw validationResult.exception
        }

        return userRepository.save(userMapper.toEntity(user)).id!!
    }

    override fun login(command: LoginCommand): String {
        // TODO: Implement login
        return ""
    }

}