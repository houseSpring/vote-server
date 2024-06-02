package house.spring.vote.domain.user.repository

import house.spring.vote.domain.user.model.User
import java.util.*

interface UserRepository {
    fun findById(id: Long): Optional<User>
    fun findByDeviceId(deviceId: String): Optional<User>
    fun save(user: User): User
}