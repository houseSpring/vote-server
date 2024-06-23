package house.spring.vote.post.controller.response

data class CreatePickResponseDto(
    val postId: String,
    val pickedPollIds: List<String>
)
