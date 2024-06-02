package house.spring.vote.application.post.service

import house.spring.vote.application.post.dto.command.CreatePostCommand
import house.spring.vote.application.post.dto.command.GenerateImageUploadUrlCommand
import house.spring.vote.application.post.dto.command.PickPostCommand
import house.spring.vote.application.post.port.EventPublisher
import house.spring.vote.domain.post.event.PickedPollEvent
import house.spring.vote.domain.post.model.PickedPoll
import house.spring.vote.domain.post.model.Poll
import house.spring.vote.domain.post.model.Post
import house.spring.vote.domain.post.model.PostId
import house.spring.vote.domain.post.repository.PickedPollRepository
import house.spring.vote.domain.post.repository.PostRepository
import house.spring.vote.domain.post.service.ObjectKeyGenerator
import house.spring.vote.domain.post.service.ObjectManager
import house.spring.vote.domain.validation.ValidationResult
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
    private val eventPublisher: EventPublisher,
) {
    suspend fun createImageUploadUrl(command: GenerateImageUploadUrlCommand): GenerateImageUploadUrlResponseDto {
        val imageKey = objectKeyGenerator.generateTempImageKey(command.userId)
        val uploadUrl = objectManager.generateUploadUrl(imageKey)
        return GenerateImageUploadUrlResponseDto(uploadUrl, imageKey)
    }

    // TODO: 코루틴을 사용할때 transactional 어노테이션 주의 필요
//    @Transactional
    suspend fun create(command: CreatePostCommand): Post {
        val post = Post.create(
            title = command.title,
            userId = command.userId,
            pickType = command.pickType,
            imageKey = command.imageKey,
            polls = command.polls.map { Poll.create(it.title) })

        val validateResult = post.validateForCreation()
        if (validateResult is ValidationResult.Error) {
            throw validateResult.exception
        }

        if (post.hasImage()) {
            post.imageKey = processImage(post.id.uuid, post.imageKey!!)
        }

        return savePost(post)
    }

    private suspend fun savePost(post: Post): Post = withContext(Dispatchers.IO) {
        postRepository.save(post)
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
        val post = postRepository.findByUuid(command.postUUID)
            ?: throw NotFoundException("${ErrorCode.POST_NOT_FOUND} (${command.postUUID})")

        val validateResult = post.validatePickedPoll(command.userId, command.pickedPollIds, pickedPollRepository)
        if (validateResult is ValidationResult.Error) {
            throw validateResult.exception
        }

        val pickedPolls = command.pickedPollIds.map {
            PickedPoll.create(post.id, it, command.userId)
        }
        pickedPollRepository.saveAll(pickedPolls)

        publishPickedPollEvent(post.id, command.pickedPollIds)
        return CreatePickResponseDto(
            command.postUUID, command.pickedPollIds
        )
    }

    private fun publishPickedPollEvent(postId: PostId, pickedPollIds: List<Long>) {
        val event = PickedPollEvent(this, postId, pickedPollIds)
        eventPublisher.publishEvent(event)
    }
}