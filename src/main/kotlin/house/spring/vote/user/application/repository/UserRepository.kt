package house.spring.vote.user.application.repository

import house.spring.vote.user.domain.model.User

interface UserRepository {
    fun findById(id: String): User?
    fun findByDeviceId(deviceId: String): User?
    fun save(user: User): User
}