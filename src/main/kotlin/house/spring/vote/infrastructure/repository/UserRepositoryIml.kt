package house.spring.vote.infrastructure.repository

import house.spring.vote.application.user.port.UserMapper
import house.spring.vote.domain.user.model.User
import house.spring.vote.domain.user.repository.UserRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class UserRepositoryIml(
    private val userJpaRepository: UserJpaRepository,
    private val userMapper: UserMapper,
) : UserRepository {
    override fun findById(id: Long): Optional<User> {
        val user = userJpaRepository.findById(id)
        return if (user.isPresent) {
            Optional.of(userMapper.toDomain(user.get()))
        } else {
            Optional.empty()
        }
    }

    override fun findByDeviceId(deviceId: String): Optional<User> {
        val user = userJpaRepository.findByDeviceId(deviceId)
        return if (user.isPresent) {
            Optional.of(userMapper.toDomain(user.get()))
        } else {
            Optional.empty()
        }

    }

    override fun save(user: User): User {
        val userEntity = userMapper.toEntity(user)
        return userMapper.toDomain(userJpaRepository.save(userEntity))
    }
}