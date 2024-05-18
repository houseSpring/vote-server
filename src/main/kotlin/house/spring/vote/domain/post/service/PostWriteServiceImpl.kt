package house.spring.vote.domain.post.service

import house.spring.vote.domain.post.dtos.command.CreatePostCommand
import house.spring.vote.domain.post.dtos.command.GenerateImageUploadUrlCommand
import house.spring.vote.domain.post.dtos.response.GenerateImageUploadUrlResponseDto
import house.spring.vote.domain.post.entity.PollEntity
import house.spring.vote.domain.post.entity.PostEntity
import house.spring.vote.domain.post.repository.PostRepository
import house.spring.vote.util.ImageKeyGenerator
import house.spring.vote.util.S3ImageUtil
import jakarta.transaction.Transactional
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

@Service
class PostWriteServiceImpl(
    private val s3ImageUtil: S3ImageUtil,
    private val imageKeyGenerator: ImageKeyGenerator,
    private val postRepository: PostRepository,
) : PostWriteService {
    override suspend fun createImageUploadUrl(command: GenerateImageUploadUrlCommand): GenerateImageUploadUrlResponseDto {
        // TODO: temp 저장소 근본 문제 해결 고민필요
        val imageKey = imageKeyGenerator.generateTempImageKey(command.userId)
        val presignedUrl = s3ImageUtil.generateUploadUrl(imageKey)
        return GenerateImageUploadUrlResponseDto(presignedUrl, imageKey)
    }

    @Transactional
    override suspend fun create(command: CreatePostCommand): String = withContext(Dispatchers.IO) {
        val postEntity = postRepository.save(
            PostEntity(
                title = command.title,
                userId = command.userId,
                pickType = command.pickType,
                imageUrl = command.imageKey,
            )
        )
        val pollEntities = command.polls.map { poll ->
            PollEntity(
                title = poll.title,
                post = postEntity
            )
        }
        postEntity.polls.addAll(pollEntities)
        val destinationKey = generateAndCopyImage(postEntity)
        postEntity.imageUrl = destinationKey

        return@withContext postEntity.uuid.toString()
    }

    private suspend fun generateAndCopyImage(postEntity: PostEntity): String {
        val destinationKey = imageKeyGenerator.generatePostImageKey(postEntity.id!!)
        try {
            s3ImageUtil.copyObject(postEntity.imageUrl!!, destinationKey)
        } catch (e: Exception) {
            throw RuntimeException("이미지 복사 실패", e)
        }
        return destinationKey
    }
}