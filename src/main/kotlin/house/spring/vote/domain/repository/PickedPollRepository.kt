package house.spring.vote.domain.repository

import house.spring.vote.infrastructure.entity.PickedPollEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PickedPollRepository : JpaRepository<PickedPollEntity, Long> {
    fun findAllByPostIdAndUserId(postId: Long, userId: Long): List<PickedPollEntity>
}