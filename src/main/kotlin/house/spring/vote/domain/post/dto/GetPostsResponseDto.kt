package house.spring.vote.domain.post.dto

import house.spring.vote.domain.post.entity.SortBy
import java.util.Date
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