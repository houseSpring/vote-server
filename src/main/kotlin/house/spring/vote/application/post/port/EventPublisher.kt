package house.spring.vote.application.post.port

interface EventPublisher {
    fun publishEvent(event: Any)
}