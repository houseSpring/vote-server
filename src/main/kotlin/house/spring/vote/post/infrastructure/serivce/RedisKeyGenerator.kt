package house.spring.vote.post.infrastructure.serivce

import house.spring.vote.post.application.port.CountKeyGenerator
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class RedisKeyGenerator(
    @Value("\${spring.redis.key-pick_poll-prefix}") private val pickPollPrefix: String,
    @Value("\${spring.redis.key-pick_post-prefix}") private val pickPostPrefix: String
) : CountKeyGenerator {

    override fun generatePickPollCountKey(pollId: String): String {
        return "$pickPollPrefix:$pollId"
    }

    override fun generatePickPollCountKeys(pollIds: List<String>): List<String> {
        return pollIds.map { generatePickPollCountKey(it) }
    }

    override fun generatePickPostCountKey(postId: String): String {
        return "$pickPostPrefix:${postId}"
    }

    override fun generatePickPostCountKeys(postIds: List<String>): List<String> {
        return postIds.map { generatePickPostCountKey(it) }
    }
}