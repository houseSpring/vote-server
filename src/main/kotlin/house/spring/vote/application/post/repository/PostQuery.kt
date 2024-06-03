package house.spring.vote.application.post.repository

import house.spring.vote.domain.post.model.SortBy
import javax.swing.SortOrder

data class PostQuery(
    val userId: Long,
    val cursor: String? = null,
    val sortBy: SortBy,
    val sortOrder: SortOrder,
    val pageSize: Int,
    val postId: Long? = null,
)
