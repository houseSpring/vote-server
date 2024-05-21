package house.spring.vote.interfaces.controller.post.response

import house.spring.vote.domain.model.SortBy
import java.time.LocalDateTime
import javax.swing.SortOrder

data class GetPostsResponseDto(
    val posts: List<GetPostsResponseDtoPost>,
    val cursor: String,
    val sortBy: SortBy,
    val sortOrder: SortOrder,
)

data class GetPostsResponseDtoPost(
    val id: String,
    val title:String,
    val imageUrl: String,
    val participantCount: Int,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)