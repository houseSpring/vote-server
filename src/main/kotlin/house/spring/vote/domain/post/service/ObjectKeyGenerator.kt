package house.spring.vote.domain.post.service


interface ObjectKeyGenerator {
    fun generateTempImageKey(userId: Long): String
    fun generateImageKey(postUUID: String): String
}