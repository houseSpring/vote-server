package house.spring.vote.post.application.port.out.repository

import house.spring.vote.post.domain.model.PickedPoll
import house.spring.vote.post.infrastructure.entity.PickedPollEntity

interface PickedPollRepository {
    fun existsByPostIdAndUserId(postId: String, userId: String): Boolean
    fun saveAll(pickedPolls: List<PickedPoll>): List<PickedPoll>
    fun findAllByUserIdAndPostIds(userId: String, postIds: List<String>): List<PickedPollEntity>
}