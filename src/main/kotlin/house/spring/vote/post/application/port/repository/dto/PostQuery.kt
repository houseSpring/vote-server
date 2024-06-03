package house.spring.vote.post.application.port.repository.dto

import house.spring.vote.post.domain.model.SortBy
import javax.swing.SortOrder

data class PostQuery(
    val userId: Long,
    val cursor: String? = null,
    val sortBy: SortBy,
    val sortOrder: SortOrder,
    val pageSize: Int,
    val postId: Long? = null,
)
