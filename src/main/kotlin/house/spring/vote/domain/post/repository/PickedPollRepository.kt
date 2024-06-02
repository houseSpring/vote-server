package house.spring.vote.domain.post.repository

import house.spring.vote.domain.post.model.PickedPoll

interface PickedPollRepository {
    fun existsByPostIdAndUserId(postId: Long, userId: Long): Boolean
    fun saveAll(pickedPolls: List<PickedPoll>): List<PickedPoll>
}