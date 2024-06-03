package house.spring.vote.post.application.port


interface ObjectKeyGenerator {
    fun generateTempImageKey(userId: Long): String
    fun generateImageKey(postUUID: String): String
}