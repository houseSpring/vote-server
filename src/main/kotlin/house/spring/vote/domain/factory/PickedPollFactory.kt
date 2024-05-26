package house.spring.vote.domain.factory

import house.spring.vote.domain.model.PickedPoll
import org.springframework.stereotype.Component

@Component
class PickedPollFactory {
    fun create(
        postId: Long,
        pollId: Long,
        userId: Long,
    ): PickedPoll = PickedPoll(
        postId = postId, pollId = pollId, userId = userId
    )


    fun reconstruct(
        id: Long,
        postId: Long,
        pollId: Long,
        userId: Long,
    ) = PickedPoll(
        id = id, postId = postId, pollId = pollId, userId = userId
    )
}