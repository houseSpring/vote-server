package house.spring.vote.domain.post.event

import house.spring.vote.domain.event.PickedPollEvent
import house.spring.vote.util.RedisKeyGenerator
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class PostEventListener(
    private val redisTemplate: RedisTemplate<String, String>,
) {

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handlePickedPollEvent(event: PickedPollEvent) {
        val pickPostKey = RedisKeyGenerator.generatePickPostKey(event.postId)
        redisTemplate.opsForValue().increment(pickPostKey)

        event.pollIds.forEach { pollId ->
            val pickPollKey = RedisKeyGenerator.generatePickPollKey(pollId)
            redisTemplate.opsForValue().increment(pickPollKey)
        }
    }
}