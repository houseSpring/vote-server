package house.spring.vote.post.application.port.out


interface ObjectKeyGenerator {
    fun generateTempImageKey(userId: String): String
    fun generateImageKey(postId: String): String
}