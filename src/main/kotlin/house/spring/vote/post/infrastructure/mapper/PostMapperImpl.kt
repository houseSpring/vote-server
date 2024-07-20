package house.spring.vote.post.infrastructure.mapper

import house.spring.vote.post.application.port.mapper.PollMapper
import house.spring.vote.post.application.port.mapper.PostMapper
import house.spring.vote.post.domain.model.Post
import house.spring.vote.post.infrastructure.entity.PostEntity
import org.springframework.stereotype.Component

@Component
class PostMapperImpl(
    private val pollMapper: PollMapper,
) : PostMapper {
    override fun toEntity(domain: Post): PostEntity {
        val entity = PostEntity(
            id = domain.id,
            title = domain.title,
            userId = domain.userId,
            pickType = domain.pickType,
            imageKey = domain.imageKey,
        )
        entity.addPolls(domain.polls.map { pollMapper.toEntity(it) })
        entity.addEvents(domain.events)
        return entity
    }

    override fun toDomain(entity: PostEntity): Post {
        return Post(
            id = entity.id,
            title = entity.title,
            userId = entity.userId,
            pickType = entity.pickType,
            imageKey = entity.imageKey,
            polls = entity.polls.map { pollMapper.toDomain(it) }
        )
    }
}