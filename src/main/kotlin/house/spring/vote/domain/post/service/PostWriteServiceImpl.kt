package house.spring.vote.domain.post.service

import house.spring.vote.domain.post.dtos.command.CreatePostCommand
import house.spring.vote.domain.post.dtos.command.GenerateImageUploadUrlCommand
import house.spring.vote.domain.post.dtos.command.PickPostCommand
import house.spring.vote.domain.post.dtos.response.CreatePickResponseDto
import house.spring.vote.domain.post.dtos.response.GenerateImageUploadUrlResponseDto
import house.spring.vote.domain.post.entity.PollEntity
import house.spring.vote.domain.post.entity.PostEntity
import house.spring.vote.domain.post.entity.PickedPollEntity
import house.spring.vote.domain.post.model.PickType
import house.spring.vote.domain.post.repository.PickedPollRepository
import house.spring.vote.domain.post.repository.PostRepository
import house.spring.vote.util.ImageKeyGenerator
import house.spring.vote.util.S3ImageUtil
import jakarta.transaction.Transactional
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import java.util.*

@Service
class PostWriteServiceImpl(
    private val s3ImageUtil: S3ImageUtil,
    private val imageKeyGenerator: ImageKeyGenerator,
    private val postRepository: PostRepository,
    private val pickedPollRepository: PickedPollRepository,
) : PostWriteService {
    override suspend fun createImageUploadUrl(command: GenerateImageUploadUrlCommand): GenerateImageUploadUrlResponseDto {
        // TODO: temp 저장소 근본 문제 해결 고민필요
        val imageKey = imageKeyGenerator.generateTempImageKey(command.userId)
        val presignedUrl = s3ImageUtil.generateUploadUrl(imageKey)
        return GenerateImageUploadUrlResponseDto(presignedUrl, imageKey)
    }

    // TODO: Poll갯수 제한이 필요할 수 있음 -> 논의 필요
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

    /*
    TODO
    - 예외처리 정형화
    - 투표 이후 집계
    */
    override fun pickPost(command: PickPostCommand): CreatePickResponseDto {
        val postEntity = postRepository.findByUuid(UUID.fromString(command.postUUID))
            ?: throw RuntimeException("투표할 게시물을 찾을 수 없습니다.")
        validatePickAble(command, postEntity)

        val pickedPollsEntities = command.pickedPollIds.map { pollId ->
            PickedPollEntity(
                postId = postEntity.id!!,
                pollId = pollId,
                userId = command.userId
            )
        }
        pickedPollRepository.saveAll(pickedPollsEntities)

        return CreatePickResponseDto(
            command.postUUID,
            command.pickedPollIds
        )
    }

    private fun validatePickAble(command: PickPostCommand, postEntity: PostEntity): Unit {
        command.pickedPollIds.forEach { pollId ->
            postEntity.polls.find { it.id == pollId }
                ?: throw RuntimeException("투표할 항목을 찾을 수 없습니다.")
        }

        if (postEntity.pickType == PickType.Single && command.pickedPollIds.size > 1) {
            throw RuntimeException("단일 선택 게시물에서는 한개의 항목만 선택할 수 있습니다.")
        } else if (postEntity.pickType == PickType.Multi && command.pickedPollIds.isEmpty()) {
            throw RuntimeException("다중 선택 게시물에서는 한개 이상의 항목을 선택해야 합니다.")
        }
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