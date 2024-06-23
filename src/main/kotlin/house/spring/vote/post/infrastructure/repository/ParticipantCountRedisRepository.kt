package house.spring.vote.post.infrastructure.repository

import house.spring.vote.common.domain.exception.internal_server.InternalServerException
import house.spring.vote.post.application.port.CountKeyGenerator
import house.spring.vote.post.application.port.repository.ParticipantCountRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class ParticipantCountRedisRepository(
    private val redisTemplate: RedisTemplate<String, String>,
    private val countKeyGenerator: CountKeyGenerator,
) : ParticipantCountRepository {
    override fun getPostIdToCountMap(postIds: List<String>): Map<String, Int> {
        return getIdToParticipantCountMap(postIds) { countKeyGenerator.generatePickPostCountKeys(it) }
    }

    override fun getPostCountById(postId: String): Int {
        return getParticipantCountById(postId) { countKeyGenerator.generatePickPostCountKey(it) }
    }

    override fun getPollIdToCountMap(ids: List<String>): Map<String, Int> {
        return getIdToParticipantCountMap(ids) { countKeyGenerator.generatePickPollCountKeys(it) }
    }

    override fun countUpPost(postId: String) {
        countUp(countKeyGenerator.generatePickPostCountKey(postId))
    }

    override fun countUpPolls(pollIds: List<String>) {
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
        postIds: List<T>, keyGenerator: (List<T>) -> List<String>,
    ): Map<T, Int> {
        val keys = keyGenerator(postIds)
        val counts = redisTemplate.opsForValue().multiGet(keys)!!.map { it?.toInt() ?: 0 }
        return postIds.zip(counts).associate { (id, count) -> id to count }
    }

    private fun getParticipantCountById(postId: String, keyGenerator: (String) -> String): Int {
        val key = keyGenerator(postId)
        return redisTemplate.opsForValue().get(key)?.toInt() ?: 0
    }

}