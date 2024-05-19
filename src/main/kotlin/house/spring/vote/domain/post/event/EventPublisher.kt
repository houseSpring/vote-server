package house.spring.vote.domain.post.event

interface EventPublisher {
    fun publishPickedPoll(postId: Long, pollIds: List<Long>)
}