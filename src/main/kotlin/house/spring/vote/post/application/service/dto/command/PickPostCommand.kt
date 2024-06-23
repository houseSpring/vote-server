package house.spring.vote.post.application.service.dto.command

data class PickPostCommand(
    val postId: String,
    val userId: String,
    val pickedPollIds: List<String>,
)
