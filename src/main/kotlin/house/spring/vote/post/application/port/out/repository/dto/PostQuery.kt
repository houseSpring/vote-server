package house.spring.vote.post.application.port.out.repository.dto

import house.spring.vote.post.domain.model.SortBy
import javax.swing.SortOrder

data class PostQuery(
    val userId: String,
    val offset: Int = 0,
    val limit: Int = 10,
    val sortBy: SortBy,
    val sortOrder: SortOrder,
    val pageSize: Int,
)
