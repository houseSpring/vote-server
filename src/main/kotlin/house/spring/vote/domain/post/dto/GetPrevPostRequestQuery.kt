package house.spring.vote.domain.post.dto

import house.spring.vote.domain.post.entity.SortBy
import javax.swing.SortOrder

data class GetPrevPostRequestQuery(
    val sortBy: SortBy,
    val sortOrder: SortOrder
)
