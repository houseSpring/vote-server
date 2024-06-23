package house.spring.vote.post.domain.model

import java.util.*

data class PickedPoll(
    val id: String,
    val postId: String,
    val pollId: String,
    val userId: String,
) {
    companion object {
        fun create(postId: String, pollId: String, userId: String): PickedPoll {
            val pickedPoll =
                PickedPoll(id = UUID.randomUUID().toString(), postId = postId, pollId = pollId, userId = userId)
            return pickedPoll
        }
    }
}
