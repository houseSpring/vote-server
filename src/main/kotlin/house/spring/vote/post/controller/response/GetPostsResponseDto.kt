package house.spring.vote.post.controller.response

import house.spring.vote.post.domain.model.SortBy
import java.time.LocalDateTime
import javax.swing.SortOrder

data class GetPostsResponseDto(
    val posts: List<GetPostsResponseDtoPost>,
    val currentPage: Int,
    val totalPages: Int,
    val sortBy: SortBy,
    val sortOrder: SortOrder,
) {
    data class GetPostsResponseDtoPost(
        val id: String,
        val title: String,
        val imageUrl: String?,
        val userId: String,
        val participantCount: Int,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime,
        val isVoted: Boolean,
    )
}