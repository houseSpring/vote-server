package house.spring.vote.application.post.service

import house.spring.vote.application.post.dto.command.CreatePostCommand
import house.spring.vote.application.post.dto.command.GenerateImageUploadUrlCommand
import house.spring.vote.application.post.dto.command.PickPostCommand
import house.spring.vote.domain.event.PickedPollEvent
import house.spring.vote.domain.factory.PollFactory
import house.spring.vote.domain.factory.PostFactory
import house.spring.vote.domain.model.PickType
import house.spring.vote.domain.model.Post
import house.spring.vote.domain.repository.PickedPollRepository
import house.spring.vote.domain.repository.PostRepository
import house.spring.vote.domain.service.ImageUrlGenerator
import house.spring.vote.infrastructure.entity.PickedPollEntity
import house.spring.vote.infrastructure.mapper.PostMapper
import house.spring.vote.infrastructure.util.S3ImageUtil
import house.spring.vote.interfaces.controller.post.response.CreatePickResponseDto
import house.spring.vote.interfaces.controller.post.response.GenerateImageUploadUrlResponseDto
import jakarta.transaction.Transactional
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.util.*

@Service
class PostWriteServiceImpl(
    private val s3ImageUtil: S3ImageUtil,
    private val imageUrlGenerator: ImageUrlGenerator,
    private val postRepository: PostRepository,
    private val pickedPollRepository: PickedPollRepository,
    private val postFactory: PostFactory,
    private val pollFactory: PollFactory,
    private val postMapper: PostMapper,
    private val eventPublisher: ApplicationEventPublisher,
) : PostWriteService {
    override suspend fun createImageUploadUrl(command: GenerateImageUploadUrlCommand): GenerateImageUploadUrlResponseDto {
        // TODO: temp 저장소 근본 문제 해결 고민필요
        val imageKey = imageUrlGenerator.generateTempImageKey(command.userId.toString())
        val presignedUrl = s3ImageUtil.generateUploadUrl(imageKey)
        return GenerateImageUploadUrlResponseDto(presignedUrl, imageKey)
    }

    // TODO: Poll갯수 제한이 필요할 수 있음 -> 논의 필요
    @Transactional
    override suspend fun create(command: CreatePostCommand): String = withContext(Dispatchers.IO) {
        val post = postFactory.create(command.title,
            command.userId,
            command.pickType,
            command.imageKey,
            command.polls.map { pollFactory.create(it.title) })
        val postEntity = postMapper.toEntity(post).let { postRepository.save(it) }

        if (command.imageKey != null) {
            postEntity.imageUrl = generateAndCopyImage(postEntity.uuid.toString(), command.imageKey)
        }
        return@withContext postEntity.uuid.toString()
    }

    /*
    TODO
    - 예외처리 정형화
    */
    @Transactional
    override fun pickPost(command: PickPostCommand): CreatePickResponseDto {
        val postEntity = postRepository.findByUuid(UUID.fromString(command.postUUID))
            ?: throw RuntimeException("투표할 게시물을 찾을 수 없습니다.")
        val post = postMapper.toDomain(postEntity)
        validatePickedPollCommand(post, command)

        val pickedPollsEntities = command.pickedPollIds.map { pollId ->
            PickedPollEntity(
                postId = postEntity.id!!, pollId = pollId, userId = command.userId
            )
        }
        pickedPollRepository.saveAll(pickedPollsEntities)
        val event = PickedPollEvent(this, postEntity.id!!, command.pickedPollIds)

        eventPublisher.publishEvent(event)
        return CreatePickResponseDto(
            command.postUUID, command.pickedPollIds
        )
    }

    private fun validatePickedPollCommand(post: Post, command: PickPostCommand) {
        if (this.hasUserAlreadyPicked(post.id!!.incrementId, command.userId)) {
            throw RuntimeException("이미 투표한 게시물입니다.")
        }
        if (this.hasInvalidPollId(post, command.pickedPollIds)) {
            throw RuntimeException("투표할 항목을 찾을 수 없습니다.")
        }
        if (!post.validatePickedPollsSize(command.pickedPollIds.size)) {
            throw RuntimeException("선택할 수 있는 항목의 갯수가 맞지 않습니다.")
        }
    }

    private fun hasInvalidPollId(post: Post, pollIds: List<Long>): Boolean {
        return pollIds.any { !post.hasPollId(it) }
    }

    private fun hasUserAlreadyPicked(postId: Long, userId: Long): Boolean {
        return pickedPollRepository.findAllByPostIdAndUserId(postId, userId).isNotEmpty()
    }

    private suspend fun generateAndCopyImage(postId: String, source: String): String {
        val destinationKey = imageUrlGenerator.generateImageKey(postId)
        try {
            s3ImageUtil.copyObject(source, destinationKey)
        } catch (e: Exception) {
            throw RuntimeException("이미지 복사 실패", e)
        }
        return destinationKey
    }
}