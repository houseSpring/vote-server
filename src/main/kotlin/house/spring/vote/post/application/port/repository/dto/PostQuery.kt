package house.spring.vote.post.application.port.repository.dto

import house.spring.vote.post.domain.model.SortBy
import javax.swing.SortOrder

data class PostQuery(
    val id: String? = null,
    val userId: String,
    val cursor: String? = null,
    val sortBy: SortBy,
    val sortOrder: SortOrder,
    val pageSize: Int,
)
