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
            uuid = domain.id.uuid,
            title = domain.title,
            userId = domain.userId,
            pickType = domain.pickType,
            imageKey = domain.imageKey,
        )
        domain.polls.forEach { poll ->
            entity.addPoll(pollMapper.toEntity(poll))
        }
        return entity
    }

    fun toDomain(entity: PostEntity): Post {
        return Post(
            id = PostId(entity.id, entity.uuid),
            title = entity.title,
            userId = entity.userId,
            pickType = entity.pickType,
            imageKey = entity.imageKey,
            polls = entity.polls.map { pollMapper.toDomain(it) }
        )
    }
}