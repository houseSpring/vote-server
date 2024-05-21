package house.spring.vote.application.post.service

import house.spring.vote.application.post.dto.query.GetPostsQuery
import house.spring.vote.domain.repository.PostRepository
import house.spring.vote.interfaces.controller.post.response.*
import house.spring.vote.util.RedisKeyGenerator
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class PostReadServiceImpl(
    private val postRepository: PostRepository,
    private val redisTemplate: RedisTemplate<String, String>,
) : PostReadService {
    override fun getPost(postUUId: String): GetPostResponseDto {
        val postEntity = postRepository.findByUuid(UUID.fromString(postUUId))
            ?: throw IllegalArgumentException("Post not found")
        val pickPostKey = RedisKeyGenerator.generatePickPostKey(postEntity.id!!)
        val pickPostCount = redisTemplate.opsForValue().get(pickPostKey)?.toInt() ?: 0

        return GetPostResponseDto(
            id = postEntity.uuid.toString(),
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
        TODO("Not yet implemented")
    }
}