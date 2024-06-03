package house.spring.vote.common.application

interface EventPublisher {
    fun publishEvent(event: Any)
}