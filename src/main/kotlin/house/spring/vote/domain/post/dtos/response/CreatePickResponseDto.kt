package house.spring.vote.domain.post.dtos.response

data class CreatePickResponseDto(
    val postId: String,
    val pickedPollIds: List<Long>
)
