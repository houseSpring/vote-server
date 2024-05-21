package house.spring.vote.domain.model

data class Post(
    val id: PostId? = null,
    val title: String,
    val userId: Long,
    val pickType: PickType,
    val imageUrl: String? = null,
    val polls: List<Poll> = mutableListOf()
) {
    fun hasImage(): Boolean = imageUrl != null
    fun hasPollId(pollId: Long): Boolean = polls.any { it.id == pollId }
    fun validatePickedPollsSize(size: Int): Boolean {
        return when (pickType) {
            PickType.Multi -> size >= 2
            PickType.Single -> size == 1
        }
    }
}