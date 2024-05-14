package house.spring.vote.domain.post.dtos.request

import house.spring.vote.domain.post.model.SortBy
import javax.swing.SortOrder

data class GetPrevPostRequestQuery(
    val sortBy: SortBy,
    val sortOrder: SortOrder
)
