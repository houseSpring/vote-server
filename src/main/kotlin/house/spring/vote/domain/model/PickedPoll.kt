package house.spring.vote.domain.model

data class PickedPoll(
    val id: Long? = null,
    val postId: Long,
    val pollId: Long,
    val userId: Long,
)
