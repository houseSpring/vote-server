package house.spring.vote.post.application.port.repository

import house.spring.vote.post.application.port.repository.dto.PostQuery
import house.spring.vote.post.domain.model.Post

interface PostRepository {
    fun save(post: Post): Post
    fun findByUuid(uuid: String): Post?
    fun findAllByQuery(query: PostQuery): List<Post>
}