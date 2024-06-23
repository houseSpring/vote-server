package house.spring.vote.post.infrastructure.repository

import house.spring.vote.post.application.port.mapper.PostMapper
import house.spring.vote.post.application.port.repository.PostRepository
import house.spring.vote.post.application.port.repository.dto.PostQuery
import house.spring.vote.post.domain.model.Post
import house.spring.vote.post.infrastructure.entity.PostEntity
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Repository
import javax.swing.SortOrder

@Repository
class PostRepositoryImpl(
    private val postJpaRepository: PostJpaRepository,
    private val postMapper: PostMapper,
) : PostRepository {
    override fun save(post: Post): Post {
        val entity = postMapper.toEntity(post)
        return postMapper.toDomain(postJpaRepository.save(entity))
    }

    override fun findById(id: String): Post? {
        val post = postJpaRepository.findById(id).orElse(null) ?: return null
        return post.let { postMapper.toDomain(it) }
    }

    override fun findAllByQuery(query: PostQuery): List<PostEntity> {
        val sort = idSortOrderToSort(query.sortOrder)
        val pageable = PageRequest.of(0, query.pageSize, sort)
        val cursor = generateCursor(query.cursor, query.id, query.sortOrder)
        return if (query.sortOrder == SortOrder.DESCENDING) {
            postJpaRepository.findAllByIdSmallerThanCursor(cursor, query.userId, pageable)
        } else {
            postJpaRepository.findAllByIdBiggerThanCursor(cursor, query.userId, pageable)
        }
    }

    override fun findEntityById(id: String): PostEntity? {
        return postJpaRepository.findById(id).orElse(null)
    }

    // TODO: 이후 픽스
    private fun generateCursor(cursor: String? = null, postId: String? = null, sortOrder: SortOrder): Long {
        println("cursor: $cursor")
        println("postId: $postId")
        println("sortOrder: $sortOrder")
        return 0L
    }

    private fun idSortOrderToSort(sortOrder: SortOrder): Sort {
        return when (sortOrder) {
            SortOrder.ASCENDING -> Sort.by(Sort.Order.asc("id"))
            SortOrder.DESCENDING -> Sort.by(Sort.Order.desc("id"))
            else -> Sort.unsorted()
        }
    }
}