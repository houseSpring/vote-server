package house.spring.vote.application.post.service

import house.spring.vote.application.error.BadRequestException
import house.spring.vote.application.error.ConflictException
import house.spring.vote.application.error.InternalServerException
import house.spring.vote.application.error.NotFoundException
import house.spring.vote.application.post.dto.command.CreatePostCommand
import house.spring.vote.application.post.dto.command.GenerateImageUploadUrlCommand
import house.spring.vote.application.post.dto.command.PickPostCommand
import house.spring.vote.domain.event.PickedPollEvent
import house.spring.vote.domain.factory.PollFactory
import house.spring.vote.domain.factory.PostFactory
import house.spring.vote.domain.model.Post
import house.spring.vote.domain.repository.PickedPollRepository
import house.spring.vote.domain.repository.PostRepository
import house.spring.vote.domain.service.ImageKeyGenerator
import house.spring.vote.domain.service.ObjectManager
import house.spring.vote.infrastructure.entity.PickedPollEntity
import house.spring.vote.infrastructure.mapper.PostMapper
import house.spring.vote.interfaces.controller.post.response.CreatePickResponseDto
import house.spring.vote.interfaces.controller.post.response.GenerateImageUploadUrlResponseDto
import jakarta.transaction.Transactional
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class PostWriteServiceImpl(
    private val objectManager: ObjectManager,
    private val imageKeyGenerator: ImageKeyGenerator,
    private val postRepository: PostRepository,
    private val pickedPollRepository: PickedPollRepository,
    private val postFactory: PostFactory,
    private val pollFactory: PollFactory,
    private val postMapper: PostMapper,
    private val eventPublisher: ApplicationEventPublisher,
) : PostWriteService {
    override suspend fun createImageUploadUrl(command: GenerateImageUploadUrlCommand): GenerateImageUploadUrlResponseDto {
        val imageKey = imageKeyGenerator.generateTempImageKey(command.userId)
        val uploadUrl = objectManager.generateUploadUrl(imageKey)
        return GenerateImageUploadUrlResponseDto(uploadUrl, imageKey)
    }

    @Transactional
    override suspend fun create(command: CreatePostCommand): String {
        val post = postFactory.create(command.title,
            command.userId,
            command.pickType,
            command.imageKey,
            command.polls.map { pollFactory.create(it.title) })

        if (post.validatePollsSize()) {
            throw BadRequestException("투표 항목 갯수가 올바르지 않습니다.")
        }

        if (post.hasImage()) {
            post.imageKey = generateAndCopyImage(post.id.uuid, post.imageKey!!)
        }

        val postEntity = withContext(Dispatchers.IO) {
            postRepository.save(postMapper.toEntity(post))
        }

        return postEntity.uuid
    }

    @Transactional
    override fun pickPost(command: PickPostCommand): CreatePickResponseDto {
        val postEntity = postRepository.findByUuid(command.postUUID)
            ?: throw NotFoundException("게시글을 찾을 수 없습니다. (${command.postUUID})")
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
        if (this.hasUserAlreadyPicked(post.id.incrementId!!, command.userId)) {
            throw ConflictException("이미 투표한 게시물입니다. (${post.id.uuid})")
        }
        if (this.hasInvalidPollId(post, command.pickedPollIds)) {
            throw NotFoundException("투표 항목을 찾을 수 없습니다. (${command.pickedPollIds.joinToString()})")
        }
        if (!post.validatePickedPollsSize(command.pickedPollIds.size)) {
            throw BadRequestException("선택할 수 있는 항목의 갯수가 맞지 않습니다.")
        }
    }

    private fun hasInvalidPollId(post: Post, pollIds: List<Long>): Boolean {
        return pollIds.any { !post.hasPollId(it) }
    }

    private fun hasUserAlreadyPicked(postId: Long, userId: Long): Boolean {
        return pickedPollRepository.findAllByPostIdAndUserId(postId, userId).isNotEmpty()
    }

    private suspend fun generateAndCopyImage(postId: String, sourceKey: String): String {
        val destinationKey = imageKeyGenerator.generateImageKey(postId)
        try {
            withContext(Dispatchers.IO) {
                objectManager.copyObject(sourceKey, destinationKey)
            }
        } catch (e: Exception) {
            throw InternalServerException("이미지 복사에 실패했습니다. ($sourceKey -> $destinationKey)")
        }
        return destinationKey
    }
}