package house.spring.vote.domain.post.repository

import house.spring.vote.domain.post.model.Post

interface PostRepository {
    fun save(post: Post): Post
    fun findByUuid(uuid: String): Post?
    fun findAllByQuery(query: PostQuery): List<Post>
}