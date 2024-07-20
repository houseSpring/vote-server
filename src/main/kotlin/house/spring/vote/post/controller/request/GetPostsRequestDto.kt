package house.spring.vote.post.controller.request

import house.spring.vote.post.domain.model.SortBy
import io.swagger.v3.oas.annotations.media.Schema
import javax.swing.SortOrder

data class GetPostsRequestDto(
    @Schema(
        description = "정렬 기준",
        required = false,
        defaultValue = "CREATED_AT",
    )
    val sortBy: SortBy = SortBy.CREATED_AT,
    @Schema(
        description = "정렬 순서",
        required = false,
        defaultValue = "DESCENDING",
    )
    val sortOrder: SortOrder = SortOrder.DESCENDING,
    val offset: Int = 0,
)
