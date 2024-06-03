package house.spring.vote.post.application.service.event

import house.spring.vote.post.domain.event.PickedPollEvent
import house.spring.vote.post.application.port.repository.ParticipantCountRepository
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Service
class PostEventListener(
    private val participantCountRepository: ParticipantCountRepository
) {

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handlePickedPollEvent(event: PickedPollEvent) {
        participantCountRepository.countUpPost(event.postId)
        participantCountRepository.countUpPolls(event.pollIds)
    }
}