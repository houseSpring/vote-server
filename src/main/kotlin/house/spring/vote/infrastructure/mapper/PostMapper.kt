package house.spring.vote.infrastructure.mapper

import house.spring.vote.domain.model.Post
import house.spring.vote.domain.model.PostId
import house.spring.vote.infrastructure.entity.PostEntity
import org.springframework.stereotype.Component

@Component
class PostMapper(
    private val pollMapper: PollMapper
) {
    fun toEntity(domain: Post): PostEntity {
        val entity = PostEntity(
            title = domain.title,
            userId = domain.userId,
            pickType = domain.pickType,
            imageUrl = domain.imageUrl,
        )
        domain.polls.forEach { poll ->
            entity.addPoll(pollMapper.toEntity(poll))
        }
        return entity
    }

    fun toDomain(entity: PostEntity): Post {
        val postId = if (entity.id != null) {
            PostId(entity.id, entity.uuid)
        } else {
            null
        }

        return Post(
            id = postId,
            title = entity.title,
            userId = entity.userId,
            pickType = entity.pickType,
            imageUrl = entity.imageUrl,
            polls = entity.polls.map { pollMapper.toDomain(it) }
        )
    }
}