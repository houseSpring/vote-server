package house.spring.vote.post.application.service

import house.spring.vote.common.domain.exception.ErrorCode
import house.spring.vote.common.domain.exception.internal_server.CopyImageFailException
import house.spring.vote.common.domain.exception.internal_server.InternalServerException
import house.spring.vote.common.domain.exception.not_found.NotFoundException
import house.spring.vote.post.application.port.ObjectKeyGenerator
import house.spring.vote.post.application.port.ObjectManager
import house.spring.vote.post.application.port.repository.PickedPollRepository
import house.spring.vote.post.application.port.repository.PostRepository
import house.spring.vote.post.application.service.dto.command.CreatePostCommand
import house.spring.vote.post.application.service.dto.command.GenerateImageUploadUrlCommand
import house.spring.vote.post.application.service.dto.command.PickPostCommand
import house.spring.vote.post.controller.response.CreatePickResponseDto
import house.spring.vote.post.controller.response.GenerateImageUploadUrlResponseDto
import house.spring.vote.post.domain.model.Poll
import house.spring.vote.post.domain.model.Post
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
) {
    suspend fun createImageUploadUrl(command: GenerateImageUploadUrlCommand): GenerateImageUploadUrlResponseDto {
        val imageKey = objectKeyGenerator.generateTempImageKey(command.userId)
        val uploadUrl = objectManager.generateUploadUrl(imageKey)
        return GenerateImageUploadUrlResponseDto(uploadUrl, imageKey)
    }

    suspend fun create(command: CreatePostCommand): String {
        val post = Post.create(
            title = command.title,
            userId = command.userId,
            pickType = command.pickType,
            imageKey = command.imageKey,
            polls = command.polls.map { Poll.create(it.title) })

        if (post.hasImage()) {
            val imageKey = processImage(post.id, post.imageKey!!)
            return savePost(post.addImageKey(imageKey)).id
        }

        return savePost(post).id
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
            throw CopyImageFailException()
        }
        return destinationKey
    }

    @Transactional
    fun pickPost(command: PickPostCommand): CreatePickResponseDto {
        val post = postRepository.findById(command.postId)
            ?: throw NotFoundException("${ErrorCode.POST_NOT_FOUND} (${command.postId})")

        val alreadyPicked = pickedPollRepository.existsByPostIdAndUserId(post.id, command.userId)
        if (alreadyPicked) {
            throw InternalServerException("${ErrorCode.ALREADY_PICKED_POST} (${command.postId})")
        }

        val pickedPolls = post.createPickedPolls(command.pickedPollIds)
        pickedPollRepository.saveAll(pickedPolls)

        return CreatePickResponseDto(
            command.postId, command.pickedPollIds
        )
    }

}