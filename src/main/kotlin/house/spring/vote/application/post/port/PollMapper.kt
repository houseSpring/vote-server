package house.spring.vote.application.post.port

import house.spring.vote.domain.post.model.Poll
import house.spring.vote.infrastructure.post.entity.PollEntity

interface PollMapper {
    fun toEntity(domain: Poll): PollEntity
    fun toDomain(entity: PollEntity): Poll
}