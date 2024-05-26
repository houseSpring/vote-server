package house.spring.vote.application.post.service

import house.spring.vote.application.error.NotFoundException
import house.spring.vote.application.post.dto.query.GetPostsQuery
import house.spring.vote.domain.repository.PostRepository
import house.spring.vote.infrastructure.entity.PostEntity
import house.spring.vote.interfaces.controller.post.response.GetPostResponseDto
import house.spring.vote.interfaces.controller.post.response.GetPostsResponseDto
import house.spring.vote.interfaces.controller.post.response.GetPostsResponseDtoPost
import house.spring.vote.interfaces.controller.post.response.PollResponseDto
import house.spring.vote.util.RedisKeyGenerator
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import javax.swing.SortOrder

@Service
class PostReadServiceImpl(
    private val postRepository: PostRepository,
    private val redisTemplate: RedisTemplate<String, String>,
) : PostReadService {
    override fun getPost(postUUId: String): GetPostResponseDto {
        val postEntity = postRepository.findByUuid(postUUId)
            ?: throw NotFoundException("게시글을 찾을 수 없습니다. ($postUUId)")
        val pickPostKey = RedisKeyGenerator.generatePickPostKey(postEntity.id!!)
        val pickPostCount = redisTemplate.opsForValue().get(pickPostKey)?.toInt() ?: 0

        return GetPostResponseDto(
            id = postEntity.uuid,
            title = postEntity.title,
            imageUrl = postEntity.imageUrl,
            participantCount = pickPostCount,
            polls = postEntity.polls.map {
                val pickPollKey = RedisKeyGenerator.generatePickPollKey(it.id!!)
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
        val sort = when (query.sortOrder) {
            SortOrder.ASCENDING -> Sort.by(Sort.Order.asc("id"))
            SortOrder.DESCENDING -> Sort.by(Sort.Order.desc("id"))
            else -> Sort.unsorted()
        }
        val pageable = PageRequest.of(0, PAGE_SIZE, sort)

        val cursor = query.cursor?.toLong() ?: if (query.sortOrder == SortOrder.DESCENDING) Long.MAX_VALUE else 0
        val posts = if (query.sortOrder == SortOrder.DESCENDING) {
            postRepository.findAllByIdBiggerThanCursor(cursor, query.userId, pageable)
        } else {
            postRepository.findAllByIdSmallerThanCursor(cursor, query.userId, pageable)
        }

        return GetPostsResponseDto(
            posts = posts.map { it.toDto() },
            cursor = posts.lastOrNull()?.id?.toString(),
            sortBy = query.sortBy,
            sortOrder = query.sortOrder
        )
    }

    private fun PostEntity.toDto(): GetPostsResponseDtoPost {
        val participantCount = RedisKeyGenerator.generatePickPostKey(this.id!!).let { key ->
            redisTemplate.opsForValue().get(key)?.toInt() ?: 0
        }
        return GetPostsResponseDtoPost(
            id = this.uuid,
            title = this.title,
            imageUrl = this.imageUrl,
            participantCount = participantCount,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt
        )
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
