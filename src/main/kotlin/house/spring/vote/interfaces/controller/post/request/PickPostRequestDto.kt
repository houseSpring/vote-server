package house.spring.vote.interfaces.controller.post.request

import jakarta.validation.constraints.NotEmpty

data class PickPostRequestDto(
    @field:NotEmpty(message = "선택한 투표가 없습니다.")
    val pickedPollIds: List<Long>
)
