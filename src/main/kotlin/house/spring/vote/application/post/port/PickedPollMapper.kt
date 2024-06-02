package house.spring.vote.application.post.port

import house.spring.vote.domain.post.model.PickedPoll
import house.spring.vote.infrastructure.post.entity.PickedPollEntity

interface PickedPollMapper {
    fun toEntity(domain: PickedPoll): PickedPollEntity
    fun toEntities(domains: List<PickedPoll>): List<PickedPollEntity>
    fun toDomain(entity: PickedPollEntity): PickedPoll
    fun toDomains(entities: List<PickedPollEntity>): List<PickedPoll>
}