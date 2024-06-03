package house.spring.vote.post.application.service.dto.command

data class PickPostCommand(
    val postUUID: String,
    val userId: Long,
    val pickedPollIds: List<Long>,
)
