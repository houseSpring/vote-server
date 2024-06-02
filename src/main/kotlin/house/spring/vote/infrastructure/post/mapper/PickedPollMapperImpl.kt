package house.spring.vote.infrastructure.post.mapper

import house.spring.vote.application.post.port.PickedPollMapper
import house.spring.vote.domain.post.model.PickedPoll
import house.spring.vote.infrastructure.post.entity.PickedPollEntity
import org.springframework.stereotype.Component

@Component
class PickedPollMapperImpl : PickedPollMapper {
    override fun toEntity(domain: PickedPoll): PickedPollEntity {
        return PickedPollEntity(
            id = domain.id,
            postId = domain.postId,
            pollId = domain.pollId,
            userId = domain.userId
        )
    }

    override fun toEntities(domains: List<PickedPoll>): List<PickedPollEntity> {
        return domains.map { toEntity(it) }
    }

    override fun toDomain(entity: PickedPollEntity): PickedPoll {
        return PickedPoll(
            id = entity.id,
            postId = entity.postId,
            pollId = entity.pollId,
            userId = entity.userId
        )
    }

    override fun toDomains(entities: List<PickedPollEntity>): List<PickedPoll> {
        return entities.map { toDomain(it) }
    }
}