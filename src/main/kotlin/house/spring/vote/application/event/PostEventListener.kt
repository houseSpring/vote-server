package house.spring.vote.application.event

import house.spring.vote.domain.event.PickedPollEvent
import house.spring.vote.domain.service.CountKeyGenerator
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Service
class PostEventListener(
    private val redisTemplate: RedisTemplate<String, String>,
    private val countKeyGenerator: CountKeyGenerator,
) {

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handlePickedPollEvent(event: PickedPollEvent) {
        val pickPostKey = countKeyGenerator.generatePickPostCountKey(event.postId)
        countUp(pickPostKey)

        event.pollIds.forEach { pollId ->
            val pickPollKey = countKeyGenerator.generatePickPollCountKey(pollId)
            countUp(pickPollKey)
        }
    }

    private fun countUp(key: String) {
        redisTemplate.opsForValue().increment(key)
    }
}