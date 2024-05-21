package house.spring.vote.infrastructure.serivce

import house.spring.vote.domain.service.ImageUrlGenerator
import house.spring.vote.util.HashUtil
import org.springframework.stereotype.Component

@Component
class S3UrlGenerator(
    private val hashUtil: HashUtil
) : ImageUrlGenerator {
    // TODO: temp 저장소 근본 문제 해결 고민필요
    override fun generateTempImageKey(imageKey: String): String = "temp/${hashUtil.hash(imageKey)}"
    override fun generateImageKey(imageKey: String): String = "images/${hashUtil.hash(imageKey)}"
}