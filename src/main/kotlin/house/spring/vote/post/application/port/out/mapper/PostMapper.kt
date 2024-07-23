package house.spring.vote.post.application.port.out.mapper

import house.spring.vote.post.domain.model.Post
import house.spring.vote.post.infrastructure.entity.PostEntity

interface PostMapper {
    fun toEntity(domain: Post): PostEntity
    fun toDomain(entity: PostEntity): Post
}