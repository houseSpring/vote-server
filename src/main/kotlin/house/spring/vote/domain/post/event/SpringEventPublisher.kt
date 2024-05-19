package house.spring.vote.domain.post.event

import house.spring.vote.domain.event.PickedPollEvent
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