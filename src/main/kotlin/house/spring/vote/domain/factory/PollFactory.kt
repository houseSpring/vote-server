package house.spring.vote.domain.factory

import house.spring.vote.domain.model.Poll
import house.spring.vote.infrastructure.entity.PollEntity
import org.springframework.stereotype.Component

@Component
class PollFactory {
    fun create(title: String): Poll = Poll(
        title = title
    )

    fun reconstitute(pollEntity: PollEntity): Poll = Poll(
        id = pollEntity.id,
        title = pollEntity.title
    )
}