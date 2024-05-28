package house.spring.vote.domain.post.event

import org.springframework.context.ApplicationEvent

class PickedPollEvent(source: Any, val postId: Long, val pollIds: List<Long>) : ApplicationEvent(source)