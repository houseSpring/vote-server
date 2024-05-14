package house.spring.vote.domain.post.dtos.request

import house.spring.vote.domain.post.model.SortBy
import javax.swing.SortOrder

data class GetPostsRequestQuery(
    val userId: Long,
    val cursor: String,
    val limit: Int,
    val sortBy: SortBy,
    val sortOrder: SortOrder
) {

}
