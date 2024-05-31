package house.spring.vote.application.post.service

import house.spring.vote.application.post.dto.command.CreatePostCommand
import house.spring.vote.application.post.dto.command.GenerateImageUploadUrlCommand
import house.spring.vote.application.post.dto.command.PickPostCommand
import house.spring.vote.application.post.port.EventPublisher
import house.spring.vote.application.post.port.PickedPollMapper
import house.spring.vote.application.post.port.PostMapper
import house.spring.vote.domain.post.event.PickedPollEvent
import house.spring.vote.domain.post.factory.PickedPollFactory
import house.spring.vote.domain.post.factory.PollFactory
import house.spring.vote.domain.post.factory.PostFactory
import house.spring.vote.domain.post.model.Post
import house.spring.vote.domain.post.repository.PickedPollRepository
import house.spring.vote.domain.post.repository.PostRepository
import house.spring.vote.domain.post.service.ObjectKeyGenerator
import house.spring.vote.domain.post.service.ObjectManager
import house.spring.vote.domain.validation.ValidationResult
import house.spring.vote.infrastructure.entity.PostEntity
import house.spring.vote.interfaces.controller.post.response.CreatePickResponseDto
import house.spring.vote.interfaces.controller.post.response.GenerateImageUploadUrlResponseDto
import house.spring.vote.util.excaption.ErrorCode
import house.spring.vote.util.excaption.InternalServerException
import house.spring.vote.util.excaption.NotFoundException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostWriteService(
    private val objectManager: ObjectManager,
    private val objectKeyGenerator: ObjectKeyGenerator,
    private val postRepository: PostRepository,
    private val pickedPollRepository: PickedPollRepository,
    private val postFactory: PostFactory,
    private val pollFactory: PollFactory,
    private val postMapper: PostMapper,
    private val pickedPollFactory: PickedPollFactory,
    private val pickedPollMapper: PickedPollMapper,
    private val eventPublisher: EventPublisher,
) {
    suspend fun createImageUploadUrl(command: GenerateImageUploadUrlCommand): GenerateImageUploadUrlResponseDto {
        val imageKey = objectKeyGenerator.generateTempImageKey(command.userId)
        val uploadUrl = objectManager.generateUploadUrl(imageKey)
        return GenerateImageUploadUrlResponseDto(uploadUrl, imageKey)
    }

    // TODO: 코루틴을 사용할때 transactional 어노테이션 주의 필요
//    @Transactional
    suspend fun create(command: CreatePostCommand): String {
        val post = postFactory.create(command.title,
            command.userId,
            command.pickType,
            command.imageKey,
            command.polls.map { pollFactory.create(it.title) })

        val validateResult = post.validateForCreation()
        if (validateResult is ValidationResult.Error) {
            throw validateResult.exception
        }

        if (post.hasImage()) {
            post.imageKey = processImage(post.id.uuid, post.imageKey!!)
        }

        return savePost(post).uuid
    }

    private suspend fun savePost(post: Post): PostEntity = withContext(Dispatchers.IO) {
        postRepository.save(postMapper.toEntity(post))
    }

    private suspend fun processImage(postId: String, sourceKey: String): String {
        val destinationKey = objectKeyGenerator.generateImageKey(postId)
        try {
            withContext(Dispatchers.IO) {
                objectManager.copyObject(sourceKey, destinationKey)
            }
        } catch (e: Exception) {
            throw InternalServerException("${ErrorCode.FAIL_ON_COPY_IMAGE} ($sourceKey -> $destinationKey)")
        }
        return destinationKey
    }

    @Transactional
    fun pickPost(command: PickPostCommand): CreatePickResponseDto {
        val postEntity = postRepository.findByUuid(command.postUUID)
            ?: throw NotFoundException("${ErrorCode.POST_NOT_FOUND} (${command.postUUID})")
        val post = postMapper.toDomain(postEntity)

        val validateResult = post.validatePickedPoll(command.userId, command.pickedPollIds, pickedPollRepository)
        if (validateResult is ValidationResult.Error) {
            throw validateResult.exception
        }

        val pickedPolls = command.pickedPollIds.map {
            pickedPollFactory.create(postEntity.id!!, it, command.userId)
        }
        pickedPollRepository.saveAll(pickedPollMapper.toEntities(pickedPolls))

        publishPickedPollEvent(postEntity.id!!, command.pickedPollIds)
        return CreatePickResponseDto(
            command.postUUID, command.pickedPollIds
        )
    }

    private fun publishPickedPollEvent(postId: Long, pickedPollIds: List<Long>) {
        val event = PickedPollEvent(this, postId, pickedPollIds)
        eventPublisher.publishEvent(event)
    }
}