package house.spring.vote.infrastructure.post.serivce

import house.spring.vote.domain.post.model.PostId
import house.spring.vote.domain.post.service.CountKeyGenerator
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class RedisKeyGenerator(
    @Value("\${spring.redis.key-pick_poll-prefix}") private val pickPollPrefix: String,
    @Value("\${spring.redis.key-pick_post-prefix}") private val pickPostPrefix: String
) : CountKeyGenerator {

    override fun generatePickPollCountKey(pollId: Long): String {
        return "$pickPollPrefix:$pollId"
    }

    override fun generatePickPollCountKeys(pollIds: List<Long>): List<String> {
        return pollIds.map { generatePickPollCountKey(it) }
    }

    override fun generatePickPostCountKey(postId: PostId): String {
        return "$pickPostPrefix:${postId.incrementId!!}"
    }

    override fun generatePickPostCountKeys(postIds: List<PostId>): List<String> {
        return postIds.map { generatePickPostCountKey(it) }
    }
}