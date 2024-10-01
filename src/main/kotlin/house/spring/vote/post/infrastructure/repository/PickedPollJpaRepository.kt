package house.spring.vote.post.infrastructure.repository

import house.spring.vote.post.infrastructure.entity.PickedPollEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PickedPollJpaRepository : JpaRepository<PickedPollEntity, Long> {
    fun existsByPostIdAndUserId(postId: String, userId: String): Boolean

    @Query("SELECT p FROM PickedPollEntity p WHERE p.userId = :userId AND p.postId IN :postIds")
    fun findAllByUserIdAndPostIds(userId: String, postIds: List<String>): List<PickedPollEntity>
}