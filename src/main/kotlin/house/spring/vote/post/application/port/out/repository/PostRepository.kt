package house.spring.vote.post.application.port.out.repository

import house.spring.vote.post.application.port.out.repository.dto.PostQuery
import house.spring.vote.post.domain.model.Post
import house.spring.vote.post.infrastructure.entity.PostEntity
import org.springframework.data.domain.Page

interface PostRepository {
    fun save(post: Post): Post
    fun findById(id: String): Post?

    fun findAllByQuery(query: PostQuery): Page<PostEntity>
    fun findEntityById(id: String): PostEntity?
}