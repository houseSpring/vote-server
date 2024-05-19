package house.spring.vote.interfaces.controller.post.request

import house.spring.vote.domain.model.SortBy
import javax.swing.SortOrder

data class GetPrevPostRequestQuery(
    val sortBy: SortBy,
    val sortOrder: SortOrder
)
