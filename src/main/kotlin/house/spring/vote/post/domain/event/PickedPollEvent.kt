package house.spring.vote.post.domain.event

import org.springframework.context.ApplicationEvent

class PickedPollEvent(source: Any, val postId: String, val pollIds: List<String>) : ApplicationEvent(source)