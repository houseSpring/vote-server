package house.spring.vote.interfaces.controller.post.response

data class GenerateImageUploadUrlResponseDto(
    val presignedUrl: String,
    val imagekey: String,
)