package house.spring.vote.domain.post.dto

import house.spring.vote.domain.post.entity.SortBy
import javax.swing.SortOrder

data class GetPostsRequestQuery(
    val userId: Long,
    val cursor: String,
    val limit: Int,
    val sortBy: SortBy,
    val sortOrder: SortOrder
) {

}
