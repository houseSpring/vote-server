package house.spring.vote.infrastructure.mapper

import house.spring.vote.domain.model.Poll
import house.spring.vote.infrastructure.entity.PollEntity
import org.springframework.stereotype.Component

@Component
class PollMapper {
    fun toEntity(domain: Poll): PollEntity {
        return PollEntity(
            id = domain.id,
            title = domain.title
        )
    }

    fun toDomain(entity: PollEntity): Poll {
        return Poll(
            id = entity.id,
            title = entity.title
        )
    }
}