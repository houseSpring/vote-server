package house.spring.vote.post.application.service.dto.command

import house.spring.vote.post.domain.model.PickType

data class CreatePostCommand(
    val title: String,
    val userId: Long,
    val pickType: PickType,
    val polls: List<CreatePostCommandPoll>,
    val imageKey: String?,
)

data class CreatePostCommandPoll(
    val title: String
)