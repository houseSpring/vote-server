package house.spring.vote.infrastructure.serivce

import house.spring.vote.domain.post.service.CountKeyGenerator
import org.springframework.stereotype.Component

@Component
class RedisKeyGenerator : CountKeyGenerator {

    override fun generatePickPollCountKey(pollId: Long): String {
        return "pick:poll:${pollId}"
    }

    override fun generatePickPollCountKeys(pollIds: List<Long>): List<String> {
        return pollIds.map { generatePickPollCountKey(it) }
    }

    override fun generatePickPostCountKey(postId: Long): String {
        return "pick:post:${postId}"
    }

    override fun generatePickPostCountKeys(postIds: List<Long>): List<String> {
        return postIds.map { generatePickPostCountKey(it) }
    }
}