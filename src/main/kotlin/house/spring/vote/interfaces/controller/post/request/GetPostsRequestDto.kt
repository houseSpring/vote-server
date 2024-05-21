package house.spring.vote.interfaces.controller.post.request

import house.spring.vote.domain.model.SortBy
import javax.swing.SortOrder

data class GetPostsRequestDto(
    val cursor: String,
    val limit: Int,
    val sortBy: SortBy = SortBy.CREATED_AT,
    val sortOrder: SortOrder = SortOrder.DESCENDING
)
