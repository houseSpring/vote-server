package house.spring.vote.application.user.repository

import house.spring.vote.domain.user.model.User

interface UserRepository {
    fun findById(id: Long): User?
    fun findByDeviceId(deviceId: String): User?
    fun save(user: User): User
}