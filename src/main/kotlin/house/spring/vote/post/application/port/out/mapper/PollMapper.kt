package house.spring.vote.post.application.port.out.mapper

import house.spring.vote.post.domain.model.Poll
import house.spring.vote.post.infrastructure.entity.PollEntity

interface PollMapper {
    fun toEntity(domain: Poll): PollEntity
    fun toDomain(entity: PollEntity): Poll
}