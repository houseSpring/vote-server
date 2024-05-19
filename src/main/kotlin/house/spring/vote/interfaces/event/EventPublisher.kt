package house.spring.vote.interfaces.event

interface EventPublisher {
    fun publishPickedPoll(postId: Long, pollIds: List<Long>)
}