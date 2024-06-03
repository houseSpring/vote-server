package house.spring.vote.post.infrastructure.repository

import house.spring.vote.post.application.port.mapper.PostMapper
import house.spring.vote.post.domain.model.Post
import house.spring.vote.post.application.port.repository.dto.PostQuery
import house.spring.vote.post.application.port.repository.PostRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Repository
import javax.swing.SortOrder

@Repository
class PostRepositoryImpl(
    private val postJpaRepository: PostJpaRepository,
    private val postMapper: PostMapper
) : PostRepository {
    override fun save(post: Post): Post {
        val entity = postMapper.toEntity(post)
        return postMapper.toDomain(postJpaRepository.save(entity))
    }

    override fun findByUuid(uuid: String): Post? {
        val post = postJpaRepository.findByUuid(uuid)
        return post?.let { postMapper.toDomain(it) }
    }

    override fun findAllByQuery(query: PostQuery): List<Post> {
        val sort = idSortOrderToSort(query.sortOrder)
        val pageable = PageRequest.of(0, query.pageSize, sort)
        val cursor = generateCursor(query.cursor, query.postId, query.sortOrder)
        val posts = if (query.sortOrder == SortOrder.DESCENDING) {
            postJpaRepository.findAllByIdSmallerThanCursor(cursor, query.userId, pageable)
        } else {
            postJpaRepository.findAllByIdBiggerThanCursor(cursor, query.userId, pageable)
        }
        return posts.map { postMapper.toDomain(it) }
    }

    private fun generateCursor(cursor: String? = null, postId: Long? = null, sortOrder: SortOrder): Long {
        return if (!cursor.isNullOrBlank()) {
            cursor.toLong()
        } else postId ?: if (sortOrder == SortOrder.DESCENDING) Long.MAX_VALUE else 0
    }

    private fun idSortOrderToSort(sortOrder: SortOrder): Sort {
        return when (sortOrder) {
            SortOrder.ASCENDING -> Sort.by(Sort.Order.asc("id"))
            SortOrder.DESCENDING -> Sort.by(Sort.Order.desc("id"))
            else -> Sort.unsorted()
        }
    }
}