package house.spring.vote.post.controller.response

import io.swagger.v3.oas.annotations.media.Schema


data class GetPrevPostResponseDto(
    @Schema(description = "다음 게시물의 Id list")
    val unReadPostIds: List<String>
)
