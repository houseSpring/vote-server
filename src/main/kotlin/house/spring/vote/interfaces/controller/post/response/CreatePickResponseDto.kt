package house.spring.vote.interfaces.controller.post.response

data class CreatePickResponseDto(
    val postId: String,
    val pickedPollIds: List<Long>
)
