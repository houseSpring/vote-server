package house.spring.vote.domain.post.dtos.response

data class GenerateImageUploadUrlResponseDto(
    val presignedUrl: String,
    val imagekey: String,
)