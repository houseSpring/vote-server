package house.spring.vote.util

object RedisKeyGenerator {
    fun generatePickPollKey(pollId: Long): String = "pick:$pollId"
    fun generatePickPostKey(postId: Long): String = "pick:post:$postId"
}