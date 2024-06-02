package house.spring.vote.infrastructure.post.mapper

import house.spring.vote.application.post.port.PollMapper
import house.spring.vote.domain.post.model.Poll
import house.spring.vote.infrastructure.post.entity.PollEntity
import org.springframework.stereotype.Component

@Component
class PollMapperImpl : PollMapper {
    override fun toEntity(domain: Poll): PollEntity {
        return PollEntity(
            id = domain.id,
            title = domain.title
        )
    }

    override fun toDomain(entity: PollEntity): Poll {
        return Poll(
            id = entity.id,
            title = entity.title
        )
    }
}