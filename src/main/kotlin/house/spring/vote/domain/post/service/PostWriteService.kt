package house.spring.vote.domain.post.service

import house.spring.vote.domain.post.dtos.command.CreatePostCommand
import house.spring.vote.domain.post.dtos.command.GenerateImageUploadUrlCommand
import house.spring.vote.domain.post.dtos.command.PickPostCommand
import house.spring.vote.domain.post.dtos.response.CreatePickResponseDto
import house.spring.vote.domain.post.dtos.response.GenerateImageUploadUrlResponseDto

interface PostWriteService {
    suspend fun createImageUploadUrl(command: GenerateImageUploadUrlCommand): GenerateImageUploadUrlResponseDto
    suspend fun create(command: CreatePostCommand): String
    fun pickPost(command: PickPostCommand): CreatePickResponseDto
}