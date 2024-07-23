package house.spring.vote.post.application.port.`in`

import house.spring.vote.post.application.service.dto.command.*

interface PostCommandService {
    suspend fun createImageUploadUrl(command: GenerateImageUploadUrlCommand): CreatePostCommandResult
    suspend fun create(command: CreatePostCommand): String
    fun pickPost(command: PickPostCommand): PickedPostCommandResult
}