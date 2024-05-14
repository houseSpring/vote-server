package house.spring.vote.domain.post.dtos.command

import house.spring.vote.domain.post.model.PickType

data class CreatePostCommand (
    val title: String,
    val pickType: PickType,
    val polls: List<Poll>
)

data class Poll (
    val title: String
)