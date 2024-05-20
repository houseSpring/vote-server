package house.spring.vote.domain.factory

import house.spring.vote.domain.model.PickType
import house.spring.vote.domain.model.Poll
import house.spring.vote.domain.model.Post
import house.spring.vote.domain.model.PostId
import org.springframework.stereotype.Component

@Component
class PostFactory {
    fun create(
        title: String,
        userId: Long,
        pickType: PickType,
        imageUrl: String?,
        polls: List<Poll>
    ): Post {
        return Post(
            title = title,
            userId = userId,
            pickType = pickType,
            imageUrl = imageUrl,
            polls = polls
        )
    }
    fun reconstitute(
        id: PostId,
        title: String,
        userId: Long,
        pickType: PickType,
        imageUrl: String,
        polls: List<Poll>
    ): Post {
        return Post(
            id = id,
            title = title,
            userId = userId,
            pickType = pickType,
            imageUrl = imageUrl,
            polls = polls
        )
    }
}