package house.spring.vote.post.application.port

interface CountKeyGenerator {
    fun generatePickPostCountKey(postId: String): String
    fun generatePickPostCountKeys(postIds: List<String>): List<String>
}