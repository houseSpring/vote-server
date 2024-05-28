package house.spring.vote.application.post.port

import house.spring.vote.application.post.dto.command.CreatePostCommand
import house.spring.vote.application.post.dto.command.GenerateImageUploadUrlCommand
import house.spring.vote.application.post.dto.command.PickPostCommand
import house.spring.vote.interfaces.controller.post.response.CreatePickResponseDto
import house.spring.vote.interfaces.controller.post.response.GenerateImageUploadUrlResponseDto

interface PostWriteService {
    suspend fun createImageUploadUrl(command: GenerateImageUploadUrlCommand): GenerateImageUploadUrlResponseDto
    suspend fun create(command: CreatePostCommand): String
    fun pickPost(command: PickPostCommand): CreatePickResponseDto
}