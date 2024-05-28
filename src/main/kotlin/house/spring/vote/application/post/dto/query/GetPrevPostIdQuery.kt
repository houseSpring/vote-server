package house.spring.vote.application.post.dto.query

import house.spring.vote.domain.post.model.SortBy
import javax.swing.SortOrder

data class GetPrevPostIdQuery(
    val userId: Long,
    val postUuid: String,
    val sortBy: SortBy,
    val sortOrder: SortOrder
)
