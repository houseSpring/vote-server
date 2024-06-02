package house.spring.vote.domain.post.model

data class Poll(
    val id: Long? = null,
    val title: String,
) {
    companion object {
        fun create(title: String): Poll {
            return Poll(title = title)
        }
    }
}
