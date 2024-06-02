package house.spring.vote.domain.post.event

import house.spring.vote.domain.post.model.PostId
import org.springframework.context.ApplicationEvent

class PickedPollEvent(source: Any, val postId: PostId, val pollIds: List<Long>) : ApplicationEvent(source)