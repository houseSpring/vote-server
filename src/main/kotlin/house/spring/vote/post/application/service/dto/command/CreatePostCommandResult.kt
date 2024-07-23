package house.spring.vote.post.application.service.dto.command

data class CreatePostCommandResult(
    val uploadUrl: String,
    val imageKey: String,
)
