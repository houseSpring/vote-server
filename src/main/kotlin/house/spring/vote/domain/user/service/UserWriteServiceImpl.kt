package house.spring.vote.domain.user.service

import house.spring.vote.domain.user.entity.UserEntity
import house.spring.vote.domain.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserWriteServiceImpl(private val userRepository: UserRepository) : UserWriteService {
    override fun join(nickname: String, deviceId: String): Long {
        val userEntity = UserEntity(nickname, deviceId)
        val createdUser = userRepository.save(userEntity)
        return createdUser.id!!
    }

    override fun login(deviceId: String): String {
        // TODO: Implement login
        return ""
    }

}