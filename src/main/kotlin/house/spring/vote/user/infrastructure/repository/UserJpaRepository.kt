package house.spring.vote.user.infrastructure.repository

import house.spring.vote.user.infrastructure.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<UserEntity, String> {
    fun findByDeviceId(deviceId: String): UserEntity?
}