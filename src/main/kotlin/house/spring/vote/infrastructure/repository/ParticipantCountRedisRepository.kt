package house.spring.vote.infrastructure.repository

import house.spring.vote.application.error.InternalServerException
import house.spring.vote.domain.repository.ParticipantCountRepository
import house.spring.vote.domain.service.CountKeyGenerator
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class ParticipantCountRedisRepository(
    private val redisTemplate: RedisTemplate<String, String>,
    private val countKeyGenerator: CountKeyGenerator,
) : ParticipantCountRepository {
    override fun getPostIdToCountMap(ids: List<Long>): Map<Long, Int> {
        return getIdToParticipantCountMap(ids) { countKeyGenerator.generatePickPostCountKeys(it) }
    }

    override fun getPostCountById(id: Long): Int {
        return getParticipantCountById(id) { countKeyGenerator.generatePickPostCountKey(it) }
    }

    override fun getPollIdToCountMap(ids: List<Long>): Map<Long, Int> {
        return getIdToParticipantCountMap(ids) { countKeyGenerator.generatePickPollCountKeys(it) }
    }

    override fun countUpPost(postId: Long) {
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
            redisTemplate.executePipelined { redisConnection ->
                keys.forEach { key ->
                    redisConnection.incr(key.toByteArray())
                }
            }
        } catch (e: Exception) {
            throw InternalServerException(e.message ?: "Redis pipeline error")
        }
    }

    private fun getIdToParticipantCountMap(
        ids: List<Long>, keyGenerator: (List<Long>) -> List<String>
    ): Map<Long, Int> {
        val keys = keyGenerator(ids)
        val counts = redisTemplate.opsForValue().multiGet(keys)!!.map { it?.toInt() ?: 0 }
        return ids.zip(counts).associate { (id, count) -> id to count }
    }

    private fun getParticipantCountById(id: Long, keyGenerator: (Long) -> String): Int {
        val key = keyGenerator(id)
        return redisTemplate.opsForValue().get(key)?.toInt() ?: 0
    }

}