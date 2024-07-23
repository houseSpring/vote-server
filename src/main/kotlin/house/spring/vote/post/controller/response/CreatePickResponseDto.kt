package house.spring.vote.post.controller.response

import house.spring.vote.post.application.service.dto.command.PickedPostCommandResult

data class CreatePickResponseDto(
    val postId: String,
    val pickedPollIds: List<String>,
){
    companion object {
        fun from(commandResult: PickedPostCommandResult): CreatePickResponseDto {
            return CreatePickResponseDto(
                postId = commandResult.postId,
                pickedPollIds = commandResult.pickedPollIds,
            )
        }
    }
}
