package house.spring.vote.user.infrastructure.repository

import house.spring.vote.user.infrastructure.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserJpaRepository : JpaRepository<UserEntity, Long> {
    fun findByDeviceId(deviceId: String): Optional<UserEntity>
}