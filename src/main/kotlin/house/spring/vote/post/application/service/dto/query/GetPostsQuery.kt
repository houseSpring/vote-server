package house.spring.vote.post.application.service.dto.query

import house.spring.vote.post.domain.model.SortBy
import javax.swing.SortOrder

data class GetPostsQuery(
    val userId: String,
    val offset: Int,
    val sortBy: SortBy,
    val sortOrder: SortOrder,
)
