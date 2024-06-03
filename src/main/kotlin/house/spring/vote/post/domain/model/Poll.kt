package house.spring.vote.post.domain.model

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
