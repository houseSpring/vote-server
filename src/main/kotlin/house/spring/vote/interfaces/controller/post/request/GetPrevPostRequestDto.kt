package house.spring.vote.interfaces.controller.post.request

import house.spring.vote.domain.model.SortBy
import io.swagger.v3.oas.annotations.media.Schema
import javax.swing.SortOrder

data class GetPrevPostRequestDto(
    @Schema(allowableValues = ["CREATED_AT"])
    val sortBy: SortBy = SortBy.CREATED_AT,
    @Schema(allowableValues = ["DESCENDING", "ASCENDING"])
    val sortOrder: SortOrder = SortOrder.DESCENDING
)
