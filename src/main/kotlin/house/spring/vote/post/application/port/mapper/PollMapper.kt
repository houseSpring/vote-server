package house.spring.vote.post.application.port.mapper

import house.spring.vote.post.domain.model.Poll
import house.spring.vote.post.infrastructure.entity.PollEntity

interface PollMapper {
    fun toEntity(domain: Poll, postId: String): PollEntity
    fun toDomain(entity: PollEntity): Poll
}