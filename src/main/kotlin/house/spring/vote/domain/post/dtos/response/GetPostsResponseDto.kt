package house.spring.vote.domain.post.dtos.response

import house.spring.vote.domain.post.model.SortBy
import java.util.*
import javax.swing.SortOrder

data class GetPostsResponseDto(
    val posts: List<PostDto>,
    val cursor: String,
    val sortBy: SortBy,
    val sortOrder: SortOrder,
) {

}

data class PostDto(
    val id: String,
    val titleInfo: TitleInfoDto,
    val createdAt: Date,
    val updatedAt: Date,
)

data class TitleInfoDto(
    val title: String,
    val imageUrl: String,
    val name: String,
)