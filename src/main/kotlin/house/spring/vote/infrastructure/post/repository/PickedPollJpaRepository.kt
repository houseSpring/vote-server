package house.spring.vote.infrastructure.post.repository

import house.spring.vote.infrastructure.post.entity.PickedPollEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PickedPollJpaRepository : JpaRepository<PickedPollEntity, Long> {
    fun existsByPostIdAndUserId(postId: Long, userId: Long): Boolean
}