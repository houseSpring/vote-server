package house.spring.vote.domain.service

interface CountKeyGenerator {
    fun generatePickPollCountKey(pollId: Long): String
    fun generatePickPostCountKey(postId: Long): String
}