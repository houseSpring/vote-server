package house.spring.vote.application.user.service

import house.spring.vote.application.user.dto.command.JoinCommand
import house.spring.vote.application.user.dto.command.LoginCommand
import house.spring.vote.domain.repository.UserRepository
import house.spring.vote.infrastructure.entity.UserEntity
import org.springframework.stereotype.Service

@Service
class UserWriteServiceImpl(private val userRepository: UserRepository) : UserWriteService {
    override fun join(command: JoinCommand): Long {
        val (nickname, deviceId) = command
        val userEntity = UserEntity(nickname, deviceId)
        val createdUser = userRepository.save(userEntity)
        return createdUser.id!!
    }

    override fun login(command: LoginCommand): String {
        // TODO: Implement login
        return ""
    }

}