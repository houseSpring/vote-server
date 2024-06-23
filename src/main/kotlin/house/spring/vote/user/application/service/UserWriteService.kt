package house.spring.vote.user.application.service

import house.spring.vote.common.domain.exception.conflict.DeviceAlreadyRegisteredException
import house.spring.vote.user.application.command.DeviceJoinCommand
import house.spring.vote.user.application.repository.UserRepository
import house.spring.vote.user.domain.model.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserWriteService(
    private val userRepository: UserRepository,
) {

    @Transactional
    fun createDeviceUser(command: DeviceJoinCommand): String {
        val alreadyExistUser = userRepository.findByDeviceId(command.deviceId)
        if (alreadyExistUser != null) {
            throw DeviceAlreadyRegisteredException("(deviceId: ${command.deviceId})")
        }

        val user = User.create(command.nickname, command.deviceId)
        return userRepository.save(user).id
    }
}