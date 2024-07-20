package house.spring.vote.post.infrastructure.repository

import house.spring.vote.post.application.port.CountKeyGenerator
import house.spring.vote.post.application.port.repository.ParticipantCountRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class RedisRepository(
    private val redisTemplate: RedisTemplate<String, String>,
    private val countKeyGenerator: CountKeyGenerator,
) : ParticipantCountRepository {
    override fun getPostsCount(postIds: List<String>): Map<String, Int> {
        if (postIds.isEmpty()) {
            return emptyMap()
        }

        val postKeys = countKeyGenerator.generatePickPostCountKeys(postIds)
        val results = redisTemplate.executePipelined { session ->
            val hashCommands = session.hashCommands()
            postKeys.forEach { postKey ->
                val byteKey = postKey.toByteArray()
                hashCommands.hGet(byteKey, byteKey)
            }
            null
        }

        return postIds.zip(results).associate { (key, value) ->
            key to ((value as String?)?.toIntOrNull() ?: 0)
        }
    }

    override fun getPostCount(postId: String): Map<String, Int> {
        val postKey = countKeyGenerator.generatePickPostCountKey(postId)
        return redisTemplate.opsForHash<String, String?>()
            .entries(postKey)
            .mapKeys { countKeyGenerator.getId(it.key) }
            .mapValues { it.value?.toIntOrNull() ?: 0 }
    }

    override fun countUpPickedPoll(postId: String, pollIds: List<String>) {
        val postKey = countKeyGenerator.generatePickPostCountKey(postId)
        redisTemplate.executePipelined { session ->
            val hashCommands = session.hashCommands()
            val postByteKey = postKey.toByteArray()
            hashCommands.hIncrBy(postByteKey, postByteKey, 1L)
            pollIds.forEach { pollId ->
                hashCommands.hIncrBy(
                    postByteKey,
                    countKeyGenerator.generatePickPollCountKey(postId, pollId).toByteArray(),
                    1L
                )
            }
            null
        }
    }
}