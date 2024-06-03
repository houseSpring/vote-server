package house.spring.vote.post.infrastructure.mapper

import house.spring.vote.post.application.port.mapper.PollMapper
import house.spring.vote.post.application.port.mapper.PostMapper
import house.spring.vote.post.domain.model.Post
import house.spring.vote.post.domain.model.PostId
import house.spring.vote.post.infrastructure.entity.PostEntity
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