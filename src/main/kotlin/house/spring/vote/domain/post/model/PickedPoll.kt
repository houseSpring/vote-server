package house.spring.vote.domain.post.model

data class PickedPoll(
    val id: Long? = null,
    val postId: Long,
    val pollId: Long,
    val userId: Long,
)
