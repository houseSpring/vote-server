package house.spring.vote.domain.event

import org.springframework.context.ApplicationEvent

class PostCreatedEvent(source: Any, val postId: Long) : ApplicationEvent(source) {
}