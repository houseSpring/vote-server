package house.spring.vote.domain.repository

import house.spring.vote.infrastructure.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<UserEntity, Long> {
    override fun findById(id: Long): Optional<UserEntity>
}