package house.spring.vote.interfaces.controller.post.response

data class GenerateImageUploadUrlResponseDto(
    val uploadUrl: String,
    val imageKey: String,
)