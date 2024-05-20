package house.spring.vote.domain.service

import house.spring.vote.util.HashUtil
import org.springframework.stereotype.Component

@Component
class ImageUrlGenerator(
    private val hashUtil: HashUtil
) {
    fun generateTempImageKey(imageKey: String): String = "temp/${hashUtil.hash(imageKey)}"
    fun generateImageKey(imageKey: String): String = "images/${hashUtil.hash(imageKey)}"
}