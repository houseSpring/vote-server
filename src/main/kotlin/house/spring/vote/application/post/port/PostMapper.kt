package house.spring.vote.application.post.port

import house.spring.vote.domain.post.model.Post
import house.spring.vote.infrastructure.post.entity.PostEntity

interface PostMapper {
    fun toEntity(domain: Post): PostEntity
    fun toDomain(entity: PostEntity): Post
}