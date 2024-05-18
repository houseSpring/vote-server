package house.spring.vote.domain.post.service

import house.spring.vote.domain.post.dtos.command.CreatePostCommand
import house.spring.vote.domain.post.dtos.command.GenerateUploadImageUrlCommand
import house.spring.vote.util.S3ImageUtil
import org.springframework.stereotype.Service

@Service
class PostWriteServiceImpl(private val s3ImageUtil: S3ImageUtil) : PostWriteService {
    override suspend fun createImageUploadUrl(command: GenerateUploadImageUrlCommand): String {
        return s3ImageUtil.generateUploadUrl(command.userId.toString())
    }

    override fun create(command: CreatePostCommand): Long {
        TODO("Not yet implemented")
    }
}