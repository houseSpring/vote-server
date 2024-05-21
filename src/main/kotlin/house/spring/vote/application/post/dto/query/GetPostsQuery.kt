package house.spring.vote.application.post.dto.query

import house.spring.vote.domain.model.SortBy
import javax.swing.SortOrder
data class GetPostsQuery(
    val cursor: String,
    val limit: Int,
    val sortBy: SortBy,
    val sortOrder: SortOrder
)
