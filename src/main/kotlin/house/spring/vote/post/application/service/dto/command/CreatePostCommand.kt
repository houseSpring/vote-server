package house.spring.vote.post.application.service.dto.command

import house.spring.vote.post.domain.model.PickType

data class CreatePostCommand(
    val title: String,
    val userId: String,
    val pickType: PickType,
    val polls: List<Poll>,
    val imageKey: String?,
) {
    data class Poll(
        val title: String
    )
}