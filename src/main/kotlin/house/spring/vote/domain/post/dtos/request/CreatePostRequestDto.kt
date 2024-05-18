package house.spring.vote.domain.post.dtos.request

import house.spring.vote.domain.post.dtos.command.CreatePostCommand
import house.spring.vote.domain.post.dtos.command.CreatePostCommandPoll
import house.spring.vote.domain.post.model.PickType

data class CreatePostRequestDto(
    val title: String,
    val pickType: PickType,
    val polls: List<PollDto>,
    val imageKey: String? = null
) {
    fun toCommand(userId:Long): CreatePostCommand {
        return CreatePostCommand(
            title = this.title,
            pickType = this.pickType,
            polls = this.polls.map { it.toCommand() },
            imageKey = this.imageKey,
            userId = userId
        )
    }
}

data class PollDto(
    val title: String,
) {
    fun toCommand(): CreatePostCommandPoll = CreatePostCommandPoll(
        title = this.title
    )
}
