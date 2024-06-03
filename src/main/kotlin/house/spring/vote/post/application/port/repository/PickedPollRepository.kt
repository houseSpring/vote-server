package house.spring.vote.post.application.port.repository

import house.spring.vote.post.domain.model.PickedPoll

interface PickedPollRepository {
    fun existsByPostIdAndUserId(postId: Long, userId: Long): Boolean
    fun saveAll(pickedPolls: List<PickedPoll>): List<PickedPoll>
}