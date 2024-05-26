package house.spring.vote.application.post.service

import house.spring.vote.application.error.NotFoundException
import house.spring.vote.application.post.dto.query.GetPostsQuery
import house.spring.vote.application.post.dto.query.GetPrevPostIdQuery
import house.spring.vote.domain.repository.PostRepository
import house.spring.vote.domain.service.CountKeyGenerator
import house.spring.vote.domain.service.ObjectManager
import house.spring.vote.infrastructure.entity.PostEntity
import house.spring.vote.interfaces.controller.post.response.*
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import javax.swing.SortOrder

@Service
class PostReadServiceImpl(
    private val postRepository: PostRepository,
    private val redisTemplate: RedisTemplate<String, String>,
    private val objectManager: ObjectManager,
    private val countKeyGenerator: CountKeyGenerator
) : PostReadService {
    override fun getPost(postUUId: String): GetPostResponseDto {
        val postEntity = postRepository.findByUuid(postUUId)
            ?: throw NotFoundException("게시글을 찾을 수 없습니다. ($postUUId)")
        val pickPostKey = countKeyGenerator.generatePickPostCountKey(postEntity.id!!)
        val pickPostCount = redisTemplate.opsForValue().get(pickPostKey)?.toInt() ?: 0

        return GetPostResponseDto(
            id = postEntity.uuid,
            title = postEntity.title,
            imageUrl = postEntity.imageKey?.let { objectManager.generateDownloadUrl(it) },
            participantCount = pickPostCount,
            polls = postEntity.polls.map {
                val pickPollKey = countKeyGenerator.generatePickPollCountKey(it.id!!)
                val pickPollCount = redisTemplate.opsForValue().get(pickPollKey)?.toInt() ?: 0
                PollResponseDto(
                    id = it.id,
                    title = it.title,
                    participantCount = pickPollCount
                )
            },
            createdAt = postEntity.createdAt,
            updatedAt = postEntity.updatedAt
        )
    }


    override fun getPosts(query: GetPostsQuery): GetPostsResponseDto {
        val sort = idSortOrderToSort(query.sortOrder)
        val pageable = PageRequest.of(0, PAGE_SIZE, sort)

        val cursor = query.cursor?.toLong() ?: if (query.sortOrder == SortOrder.DESCENDING) Long.MAX_VALUE else 0
        val posts = if (query.sortOrder == SortOrder.DESCENDING) {
            postRepository.findAllByIdSmallerThanCursor(cursor, query.userId, pageable)
        } else {
            postRepository.findAllByIdBiggerThanCursor(cursor, query.userId, pageable)
        }

        return GetPostsResponseDto(
            posts = posts.map { it.toDto() },
            cursor = posts.lastOrNull()?.id?.toString(),
            sortBy = query.sortBy,
            sortOrder = query.sortOrder
        )
    }

    override fun getPrevPostIds(query: GetPrevPostIdQuery): GetPrevPostResponseDto {
        val post = postRepository.findByUuid(query.postUuid)
            ?: throw NotFoundException("게시글을 찾을 수 없습니다. ($query.postUuid)")

        val sort = idSortOrderToSort(query.sortOrder)
        val pageable = PageRequest.of(0, PAGE_SIZE, sort)
        val prevPost = if (query.sortOrder == SortOrder.DESCENDING) {
            postRepository.findAllByIdSmallerThanCursor(post.id!!, query.userId, pageable)
        } else {
            postRepository.findAllByIdBiggerThanCursor(post.id!!, query.userId, pageable)
        }

        return GetPrevPostResponseDto(
            unReadPostIds = prevPost.map { it.uuid }
        )
    }

    private fun idSortOrderToSort(sortOrder: SortOrder): Sort {
        return when (sortOrder) {
            SortOrder.ASCENDING -> Sort.by(Sort.Order.asc("id"))
            SortOrder.DESCENDING -> Sort.by(Sort.Order.desc("id"))
            else -> Sort.unsorted()
        }
    }

    private fun PostEntity.toDto(): GetPostsResponseDtoPost {
        val participantCount = countKeyGenerator.generatePickPostCountKey(this.id!!).let { key ->
            redisTemplate.opsForValue().get(key)?.toInt() ?: 0
        }
        return GetPostsResponseDtoPost(
            id = this.uuid,
            title = this.title,
            imageUrl = this.imageKey?.let { objectManager.generateDownloadUrl(it) },
            participantCount = participantCount,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt
        )
    }


    companion object {
        private const val PAGE_SIZE = 20
    }
}
