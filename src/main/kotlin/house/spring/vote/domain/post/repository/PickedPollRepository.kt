package house.spring.vote.domain.post.repository

import house.spring.vote.domain.post.entity.PickedPollEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PickedPollRepository : JpaRepository<PickedPollEntity, Long> {
    fun findAllByPostIdAndUserId(postId: Long, userId: Long): List<PickedPollEntity>
}