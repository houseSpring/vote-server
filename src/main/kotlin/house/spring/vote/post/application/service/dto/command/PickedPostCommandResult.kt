package house.spring.vote.post.application.service.dto.command

data class PickedPostCommandResult(
    val postId:String,
    val pickedPollIds:List<String>
)
