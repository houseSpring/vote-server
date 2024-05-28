package house.spring.vote.domain.user.repository

import house.spring.vote.infrastructure.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<UserEntity, Long> {
    override fun findById(id: Long): Optional<UserEntity>
    fun existsByDeviceId(deviceId: String): Boolean
}