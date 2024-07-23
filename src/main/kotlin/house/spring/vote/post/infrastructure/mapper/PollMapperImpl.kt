package house.spring.vote.post.infrastructure.mapper

import house.spring.vote.post.application.port.out.mapper.PollMapper
import house.spring.vote.post.domain.model.Poll
import house.spring.vote.post.infrastructure.entity.PollEntity
import org.springframework.stereotype.Component

@Component
class PollMapperImpl : PollMapper {
    override fun toEntity(domain: Poll): PollEntity {
        return PollEntity(
            id = domain.id,
            title = domain.title,
        )
    }

    override fun toDomain(entity: PollEntity): Poll {
        return Poll(
            id = entity.id,
            title = entity.title
        )
    }
}