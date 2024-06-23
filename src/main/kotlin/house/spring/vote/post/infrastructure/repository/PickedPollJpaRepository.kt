package house.spring.vote.post.infrastructure.repository

import house.spring.vote.post.infrastructure.entity.PickedPollEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PickedPollJpaRepository : JpaRepository<PickedPollEntity, Long> {
    fun existsByPostIdAndUserId(postId: String, userId: String): Boolean
}