package house.spring.vote.post.application.service.event

import house.spring.vote.post.application.port.out.repository.ParticipantCountRepository
import house.spring.vote.post.domain.event.PickedPollEvent
import org.springframework.stereotype.Service
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Service
class PostEventListener(
    private val participantCountRepository: ParticipantCountRepository,
) {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handlePickedPollEvent(event: PickedPollEvent) {
        participantCountRepository.countUpPickedPoll(event.postId, event.pollIds)
    }
}