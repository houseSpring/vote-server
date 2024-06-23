package house.spring.vote.post.application.port.repository

import house.spring.vote.post.application.port.repository.dto.PostQuery
import house.spring.vote.post.domain.model.Post
import house.spring.vote.post.infrastructure.entity.PostEntity

interface PostRepository {
    fun save(post: Post): Post
    fun findById(id: String): Post?

    fun findAllByQuery(query: PostQuery): List<PostEntity>
    fun findEntityById(id: String): PostEntity?
}