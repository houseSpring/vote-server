package house.spring.vote.infrastructure.event

import house.spring.vote.domain.event.PickedPollEvent
import house.spring.vote.interfaces.event.EventPublisher
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class SpringEventPublisher(
    private val applicationEventPublisher: ApplicationEventPublisher
) : EventPublisher {
    override fun publishPickedPoll(postId: Long, pollIds: List<Long>) {
        val event = PickedPollEvent(this, postId, pollIds)
        applicationEventPublisher.publishEvent(event)
    }
}