package house.spring.vote.post.application.service.dto.query

import house.spring.vote.post.domain.model.SortBy
import javax.swing.SortOrder

data class GetPrevPostIdQuery(
    val userId: Long,
    val postUuid: String,
    val sortBy: SortBy,
    val sortOrder: SortOrder
)
