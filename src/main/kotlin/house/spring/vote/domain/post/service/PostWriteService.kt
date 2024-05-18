package house.spring.vote.domain.post.service

import house.spring.vote.domain.post.dtos.command.CreatePostCommand
import house.spring.vote.domain.post.dtos.command.GenerateUploadImageUrlCommand

interface PostWriteService {
    suspend fun createImageUploadUrl(command: GenerateUploadImageUrlCommand): String
    fun create(command: CreatePostCommand): Long
}