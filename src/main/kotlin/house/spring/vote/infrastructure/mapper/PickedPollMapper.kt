package house.spring.vote.infrastructure.mapper

import house.spring.vote.domain.model.PickedPoll
import house.spring.vote.infrastructure.entity.PickedPollEntity
import org.springframework.stereotype.Component

@Component
class PickedPollMapper {
    fun toEntity(domain: PickedPoll): PickedPollEntity {
        return PickedPollEntity(
            id = domain.id,
            postId = domain.postId,
            pollId = domain.pollId,
            userId = domain.userId
        )
    }

    fun toDomain(entity: PickedPollEntity): PickedPoll {
        return PickedPoll(
            id = entity.id,
            postId = entity.postId,
            pollId = entity.pollId,
            userId = entity.userId
        )
    }
}