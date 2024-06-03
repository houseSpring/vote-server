package house.spring.vote.post.domain.model

data class PickedPoll(
    val id: Long? = null,
    val postId: Long,
    val pollId: Long,
    val userId: Long,
) {
    companion object {
        fun create(postId: PostId, pollId: Long, userId: Long): PickedPoll {
            return PickedPoll(postId = postId.incrementId!!, pollId = pollId, userId = userId)
        }
    }
}
