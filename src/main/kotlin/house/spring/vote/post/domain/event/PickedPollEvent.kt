package house.spring.vote.post.domain.event

class PickedPollEvent(
    val postId: String,
    val pollIds: List<String>
)