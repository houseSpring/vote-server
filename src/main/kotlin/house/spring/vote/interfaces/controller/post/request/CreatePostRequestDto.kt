package house.spring.vote.interfaces.controller.post.request

import house.spring.vote.application.post.dto.command.CreatePostCommand
import house.spring.vote.application.post.dto.command.CreatePostCommandPoll
import house.spring.vote.domain.post.model.PickType
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty

@Schema(description = "게시글 생성 요청")
data class CreatePostRequestDto(
    @field:NotBlank(message = "title은 공백일 수 없습니다.")
    val title: String,
    val pickType: PickType,
    @field:NotEmpty(message = "polls는 비어있을 수 없습니다.")
    val polls: List<CreatePostRequestDtoPoll>,
    @Schema(
        description = "upload시 발급받은 이미지 키",
        required = false
    )
    val imageKey: String? = null
)

data class CreatePostRequestDtoPoll(
    @field:NotBlank(message = "title은 공백일 수 없습니다.")
    val title: String,
)


fun CreatePostRequestDto.toCommand(userId: Long): CreatePostCommand {
    return CreatePostCommand(
        title = title,
        userId = userId,
        pickType = pickType,
        polls = polls.map { CreatePostCommandPoll(it.title) },
        imageKey = imageKey
    )
}