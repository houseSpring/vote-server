package house.spring.vote.util

import org.springframework.stereotype.Component

@Component
class ImageKeyGenerator(
    private val hashUtil: HashUtil
) {
    fun generateTempImageKey(userId: Long): String {
        return "temp/${hashUtil.hash(userId.toString())}"
    }

    fun generatePostImageKey(postId: Long): String {
        return "post/$postId"
    }
}