package house.spring.vote.user.application.service

import house.spring.vote.common.application.EventPublisher
import house.spring.vote.common.application.TokenProvider
import house.spring.vote.common.domain.CurrentUser
import house.spring.vote.common.domain.exception.UnAuthorizedException
import house.spring.vote.common.domain.exception.conflict.DeviceAlreadyRegisteredException
import house.spring.vote.user.application.command.DeviceJoinCommand
import house.spring.vote.user.application.command.DeviceLoginCommand
import house.spring.vote.user.application.port.`in`.UserCommandService
import house.spring.vote.user.application.repository.UserRepository
import house.spring.vote.user.domain.event.UserCreatedEvent
import house.spring.vote.user.domain.model.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserCommandServiceImpl(
    private val userRepository: UserRepository,
    private val tokenProvider: TokenProvider,
    private val applicationEventPublisher: EventPublisher,
) : UserCommandService {

    @Transactional
    override fun createDeviceUser(command: DeviceJoinCommand): String {
        val alreadyExistUser = userRepository.findByDeviceId(command.deviceId)
        if (alreadyExistUser != null) {
            throw DeviceAlreadyRegisteredException("(deviceId: ${command.deviceId})")
        }

        val user = User.create(
            nickname = command.nickname,
            deviceId = command.deviceId
        )
        userRepository.save(user)
        // todo : 도메인 이벤트로 변경 필요
        applicationEventPublisher.publishEvent(UserCreatedEvent(user.id))
        return tokenProvider.generateToken(CurrentUser(user.id, user.deviceId))
    }

    override fun loginDeviceUser(command: DeviceLoginCommand): String {
        val user = userRepository.findByDeviceId(command.deviceId)
            ?: throw UnAuthorizedException("유저를 찾을 수 없습니다. (deviceId: ${command.deviceId})")
        return tokenProvider.generateToken(CurrentUser(user.id, user.deviceId))
    }
}