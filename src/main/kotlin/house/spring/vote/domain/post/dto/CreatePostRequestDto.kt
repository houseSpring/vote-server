package house.spring.vote.domain.post.dto

data class CreatePostRequestDto(
    val title: String,
    val pickType: String, // 단일 선택, 다중 선택
    val polls: List<PollDto>,
)

data class PollDto(
    val title: String,
)
