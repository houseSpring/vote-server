package house.spring.vote.domain.post.service

import house.spring.vote.domain.post.dtos.command.CreatePostCommand

interface PostWriteService {
    fun create(command: CreatePostCommand): Long
}