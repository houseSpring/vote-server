package house.spring.vote.infrastructure.common

import house.spring.vote.application.post.port.EventPublisher
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class SpringEventPublisher(
    private val applicationEventPublisher: ApplicationEventPublisher
) : EventPublisher {
    override fun publishEvent(event: Any) {
        applicationEventPublisher.publishEvent(event)
    }
}