package house.spring.vote.domain.service

interface CountKeyGenerator {
    fun generatePickPollCountKey(pollId: Long): String
    fun generatePickPollCountKeys(pollIds: List<Long>): List<String>
    fun generatePickPostCountKey(postId: Long): String
    fun generatePickPostCountKeys(postIds: List<Long>): List<String>
}