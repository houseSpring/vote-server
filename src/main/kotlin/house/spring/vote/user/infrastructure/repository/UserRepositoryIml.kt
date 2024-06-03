package house.spring.vote.user.infrastructure.repository

import house.spring.vote.user.application.port.UserMapper
import house.spring.vote.user.domain.model.User
import house.spring.vote.user.application.repository.UserRepository
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryIml(
    private val userJpaRepository: UserJpaRepository,
    private val userMapper: UserMapper,
) : UserRepository {
    override fun findById(id: Long): User? {
        val user = userJpaRepository.findById(id)
        return if (user.isPresent) userMapper.toDomain(user.get()) else null
    }

    override fun findByDeviceId(deviceId: String): User? {
        val user = userJpaRepository.findByDeviceId(deviceId)
        return if (user.isPresent) userMapper.toDomain(user.get()) else null
    }

    override fun save(user: User): User {
        val userEntity = userMapper.toEntity(user)
        return userMapper.toDomain(userJpaRepository.save(userEntity))
    }
}