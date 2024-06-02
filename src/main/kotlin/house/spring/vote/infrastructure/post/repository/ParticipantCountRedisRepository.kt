package house.spring.vote.infrastructure.post.repository

import house.spring.vote.domain.post.model.PostId
import house.spring.vote.domain.post.repository.ParticipantCountRepository
import house.spring.vote.domain.post.service.CountKeyGenerator
import house.spring.vote.util.excaption.InternalServerException
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class ParticipantCountRedisRepository(
    private val redisTemplate: RedisTemplate<String, String>,
    private val countKeyGenerator: CountKeyGenerator,
) : ParticipantCountRepository {
    override fun getPostIdToCountMap(postIds: List<PostId>): Map<PostId, Int> {
        return getIdToParticipantCountMap(postIds) { countKeyGenerator.generatePickPostCountKeys(it) }
    }

    override fun getPostCountById(postId: PostId): Int {
        return getParticipantCountById(postId) { countKeyGenerator.generatePickPostCountKey(it) }
    }

    override fun getPollIdToCountMap(ids: List<Long>): Map<Long, Int> {
        return getIdToParticipantCountMap(ids) { countKeyGenerator.generatePickPollCountKeys(it) }
    }

    override fun countUpPost(postId: PostId) {
        countUp(countKeyGenerator.generatePickPostCountKey(postId))
    }

    override fun countUpPolls(pollIds: List<Long>) {
        val keys = countKeyGenerator.generatePickPollCountKeys(pollIds)
        countUpMultiple(keys)
    }

    private fun countUp(key: String) {
        redisTemplate.opsForValue().increment(key)
    }

    private fun countUpMultiple(keys: List<String>) {
        try {
            redisTemplate.executePipelined {
                val operations = redisTemplate.opsForValue()
                keys.forEach { key ->
                    operations.increment(key)
                }
                null
            }
        } catch (e: Exception) {
            throw InternalServerException(e.message ?: "Redis pipeline error")
        }
    }

    private fun <T> getIdToParticipantCountMap(
        postIds: List<T>, keyGenerator: (List<T>) -> List<String>
    ): Map<T, Int> {
        val keys = keyGenerator(postIds)
        val counts = redisTemplate.opsForValue().multiGet(keys)!!.map { it?.toInt() ?: 0 }
        return postIds.zip(counts).associate { (id, count) -> id to count }
    }

    private fun getParticipantCountById(postId: PostId, keyGenerator: (PostId) -> String): Int {
        val key = keyGenerator(postId)
        return redisTemplate.opsForValue().get(key)?.toInt() ?: 0
    }

}