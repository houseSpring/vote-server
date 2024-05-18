package house.spring.vote.domain.post.dtos.command

import house.spring.vote.domain.post.model.PickType

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