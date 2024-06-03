package house.spring.vote.post.controller.response

import java.time.LocalDateTime

data class GetPostResponseDto(
    val id: String,
    val title: String,
    val imageUrl: String?,
    val participantCount: Int,
    val polls: List<PollResponseDto>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)

data class PollResponseDto(
    val id: Long,
    val title: String,
    val participantCount: Int,
)
