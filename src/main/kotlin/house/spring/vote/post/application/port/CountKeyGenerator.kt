package house.spring.vote.post.application.port

interface CountKeyGenerator {
    fun generatePickPollCountKey(pollId: String): String
    fun generatePickPollCountKeys(pollIds: List<String>): List<String>
    fun generatePickPostCountKey(postId: String): String
    fun generatePickPostCountKeys(postIds: List<String>): List<String>
}