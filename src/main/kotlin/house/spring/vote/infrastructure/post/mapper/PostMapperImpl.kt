package house.spring.vote.infrastructure.post.mapper

import house.spring.vote.application.post.port.PollMapper
import house.spring.vote.application.post.port.PostMapper
import house.spring.vote.domain.post.model.Post
import house.spring.vote.domain.post.model.PostId
import house.spring.vote.infrastructure.post.entity.PostEntity
import org.springframework.stereotype.Component

@Component
class PostMapperImpl(
    private val pollMapper: PollMapper
) : PostMapper {
    override fun toEntity(domain: Post): PostEntity {
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

    override fun toDomain(entity: PostEntity): Post {
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