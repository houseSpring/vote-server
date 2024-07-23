package house.spring.vote.post.application.port.out.mapper

import house.spring.vote.post.domain.model.PickedPoll
import house.spring.vote.post.infrastructure.entity.PickedPollEntity

interface PickedPollMapper {
    fun toEntity(domain: PickedPoll): PickedPollEntity
    fun toEntities(domains: List<PickedPoll>): List<PickedPollEntity>
    fun toDomain(entity: PickedPollEntity): PickedPoll
    fun toDomains(entities: List<PickedPollEntity>): List<PickedPoll>
}