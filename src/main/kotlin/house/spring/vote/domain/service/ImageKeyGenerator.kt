package house.spring.vote.domain.service


interface ImageKeyGenerator {
    fun generateTempImageKey(userId: Long): String
    fun generateImageKey(postUUID: String): String
}