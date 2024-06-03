package house.spring.vote.common.infrastructure

import house.spring.vote.common.application.EventPublisher
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