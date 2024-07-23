package house.spring.vote.post.application.port.out

interface CountKeyGenerator {
    fun generatePickPostCountKey(postId: String): String
    fun generatePickPostCountKeys(postIds: List<String>): List<String>
    fun generatePickPollCountKey(postId: String, pollId: String): String
    fun generatePickPollCountKeys(postId: String, pollIds: List<String>): List<String>
    fun getId(key: String): String
}