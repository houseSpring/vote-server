package house.spring.vote.domain.model

data class Post(
    val id: PostId? = null,
    val title: String,
    val userId: Long,
    val pickType: PickType,
    val imageUrl: String? = null,
    val polls: List<Poll> = mutableListOf()
) {
    fun addPoll(poll: Poll) {
        polls.addLast(poll)
    }
}