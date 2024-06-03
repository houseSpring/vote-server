package house.spring.vote.post.domain.event

import house.spring.vote.post.domain.model.PostId
import org.springframework.context.ApplicationEvent

class PickedPollEvent(source: Any, val postId: PostId, val pollIds: List<Long>) : ApplicationEvent(source)