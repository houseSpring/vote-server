package house.spring.vote.infrastructure.serivce

import house.spring.vote.domain.service.CountKeyGenerator
import org.springframework.stereotype.Component

@Component
class RedisKeyGenerator : CountKeyGenerator {

    override fun generatePickPollCountKey(pollId: Long): String {
        return "pick:poll:${pollId}"
    }

    override fun generatePickPostCountKey(postId: Long): String {
        return "pick:post:${postId}"
    }
}