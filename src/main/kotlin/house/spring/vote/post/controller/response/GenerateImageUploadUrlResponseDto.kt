package house.spring.vote.post.controller.response

data class GenerateImageUploadUrlResponseDto(
    val uploadUrl: String,
    val imageKey: String,
)