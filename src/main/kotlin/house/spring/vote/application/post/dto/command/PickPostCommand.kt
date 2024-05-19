package house.spring.vote.application.post.dto.command

data class PickPostCommand(
    val postUUID: String,
    val userId: Long,
    val pickedPollIds: List<Long>,
)
