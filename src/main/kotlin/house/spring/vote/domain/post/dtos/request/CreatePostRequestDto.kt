package house.spring.vote.domain.post.dtos.request

import house.spring.vote.domain.post.model.PickType

data class CreatePostRequestDto(
    val title: String,
    val pickType: PickType, // 단일 선택, 다중 선택
    val polls: List<PollDto>,
)

data class PollDto(
    val title: String,
)
