package house.spring.vote.interfaces.controller.post.response

import house.spring.vote.domain.model.SortBy
import java.util.*
import javax.swing.SortOrder

data class GetPostsResponseDto(
    val posts: List<GetPostsResponseDtoPost>,
    val cursor: String,
    val sortBy: SortBy,
    val sortOrder: SortOrder,
)

data class GetPostsResponseDtoPost(
    val id: String,
    val titleInfo: TitleInfoDto,
    val createdAt: Date,
    val updatedAt: Date,
)

data class TitleInfoDto(
    val imageUrl: String,
    val name: String,
)