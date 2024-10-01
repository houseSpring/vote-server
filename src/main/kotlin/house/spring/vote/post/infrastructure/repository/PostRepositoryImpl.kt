package house.spring.vote.post.infrastructure.repository

import house.spring.vote.post.application.port.out.mapper.PostMapper
import house.spring.vote.post.application.port.out.repository.PostRepository
import house.spring.vote.post.application.port.out.repository.dto.PostQuery
import house.spring.vote.post.domain.model.Post
import house.spring.vote.post.infrastructure.entity.PostEntity
import org.springframework.data.domain.Page
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
        return postMapper.toDomain(post)
    }

    override fun findAllByQuery(query: PostQuery): Page<PostEntity> {
        val pageable = PageRequest.of(query.offset, query.pageSize, createdAtSortOrderToSort(query.sortOrder))
        return postJpaRepository.findAll(pageable)
    }


    private fun createdAtSortOrderToSort(sortOrder: SortOrder): Sort {
        return when (sortOrder) {
            SortOrder.ASCENDING -> Sort.by(Sort.Order.asc("createdAt"))
            SortOrder.DESCENDING -> Sort.by(Sort.Order.desc("createdAt"))
            else -> Sort.unsorted()
        }
    }

    override fun findEntityById(id: String): PostEntity? {
        return postJpaRepository.findById(id).orElse(null)
    }
}