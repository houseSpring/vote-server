package house.spring.vote.post.application.port.repository

import house.spring.vote.post.domain.model.PickedPoll

interface PickedPollRepository {
    fun existsByPostIdAndUserId(postId: String, userId: String): Boolean
    fun saveAll(pickedPolls: List<PickedPoll>): List<PickedPoll>
}