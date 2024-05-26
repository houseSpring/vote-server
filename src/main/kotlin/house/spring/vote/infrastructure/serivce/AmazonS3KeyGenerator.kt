package house.spring.vote.infrastructure.serivce

import house.spring.vote.domain.service.ImageKeyGenerator
import house.spring.vote.util.HashUtil
import org.springframework.stereotype.Component

@Component
class AmazonS3KeyGenerator(
    private val hashUtil: HashUtil
) : ImageKeyGenerator {
    // TODO: temp 저장소 근본 문제 해결 고민필요
    override fun generateTempImageKey(userId: Long): String = "temp/${hashUtil.hash(userId.toString())}"
    override fun generateImageKey(postUUID: String): String = "images/${postUUID}"
}