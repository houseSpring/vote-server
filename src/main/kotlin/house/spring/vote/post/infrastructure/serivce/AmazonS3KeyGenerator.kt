package house.spring.vote.post.infrastructure.serivce

import house.spring.vote.post.application.port.ObjectKeyGenerator
import house.spring.vote.common.infrastructure.HashUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class AmazonS3KeyGenerator(
    private val hashUtil: HashUtil,
    @Value("\${aws.s3.temp-image-path}") private val tempImagePath: String,
    @Value("\${aws.s3.image-path}") private val imagePath: String
) : ObjectKeyGenerator {
    // TODO: temp 저장소 근본 문제 해결 고민필요
    override fun generateTempImageKey(userId: Long): String = "$tempImagePath/${hashUtil.hash(userId.toString())}"
    override fun generateImageKey(postUUID: String): String = "$imagePath/${postUUID}"
}