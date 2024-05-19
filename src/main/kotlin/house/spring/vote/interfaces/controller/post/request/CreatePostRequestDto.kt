package house.spring.vote.interfaces.controller.post.request

import house.spring.vote.application.post.dto.command.CreatePostCommand
import house.spring.vote.application.post.dto.command.CreatePostCommandPoll
import house.spring.vote.domain.model.PickType

data class CreatePostRequestDto(
    val title: String,
    val pickType: PickType,
    val polls: List<CreatePostRequestDtoPoll>,
    val imageKey: String? = null
)

data class CreatePostRequestDtoPoll(
    val title: String,
)


fun CreatePostRequestDto.toCommand(userId: Long): CreatePostCommand {
    return CreatePostCommand(
        title = title,
        userId = userId,
        pickType = pickType,
        polls = polls.map { CreatePostCommandPoll(it.title) },
        imageKey = imageKey
    )
}

fun CreatePostRequestDtoPoll.toCommand(): CreatePostCommandPoll = CreatePostCommandPoll(title)