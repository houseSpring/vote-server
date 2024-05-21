package house.spring.vote.domain.service

interface ImageUrlGenerator {
    fun generateTempImageKey(imageKey: String): String
    fun generateImageKey(imageKey: String): String
}