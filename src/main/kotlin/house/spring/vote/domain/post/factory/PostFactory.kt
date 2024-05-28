package house.spring.vote.domain.post.factory

import house.spring.vote.domain.post.model.PickType
import house.spring.vote.domain.post.model.Poll
import house.spring.vote.domain.post.model.Post
import house.spring.vote.domain.post.model.PostId
import org.springframework.stereotype.Component

@Component
class PostFactory {
    fun create(
        title: String,
        userId: Long,
        pickType: PickType,
        imageKey: String?,
        polls: List<Poll>
    ): Post {
        return Post(
            title = title,
            userId = userId,
            pickType = pickType,
            imageKey = imageKey,
            polls = polls
        )
    }

    fun reconstitute(
        id: PostId,
        title: String,
        userId: Long,
        pickType: PickType,
        imageKey: String,
        polls: List<Poll>
    ): Post {
        return Post(
            id = id,
            title = title,
            userId = userId,
            pickType = pickType,
            imageKey = imageKey,
            polls = polls
        )
    }
}