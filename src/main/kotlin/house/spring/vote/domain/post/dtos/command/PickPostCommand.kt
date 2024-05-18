package house.spring.vote.domain.post.dtos.command

data class PickPostCommand(
    val postUUID: String,
    val userId: Long,
    val pickedPollIds: List<Long>,
)
