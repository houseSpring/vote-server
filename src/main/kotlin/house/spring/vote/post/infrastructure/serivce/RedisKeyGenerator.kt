package house.spring.vote.post.infrastructure.serivce

import house.spring.vote.post.application.port.CountKeyGenerator
import org.springframework.stereotype.Component

@Component
class RedisKeyGenerator() : CountKeyGenerator {

    override fun generatePickPostCountKey(postId: String): String {
        return "postId:$postId"
    }

    override fun generatePickPostCountKeys(postIds: List<String>): List<String> {
        return postIds.map { generatePickPostCountKey(it) }
    }

    override fun generatePickPollCountKey(postId: String, pollId: String): String {
        return "pollId:$pollId"
    }

    override fun generatePickPollCountKeys(postId: String, pollIds: List<String>): List<String> {
        return pollIds.map { generatePickPollCountKey(postId, it) }
    }

    override fun getId(key: String): String {
        return key.split(":").last()
    }
}