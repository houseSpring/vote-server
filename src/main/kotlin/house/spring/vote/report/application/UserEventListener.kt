package house.spring.vote.report.application

import house.spring.vote.report.domain.ReportEntity
import house.spring.vote.report.infrastructure.ReportJpaRepository
import house.spring.vote.user.domain.event.UserCreatedEvent
import org.springframework.stereotype.Service
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Service
class UserEventListener(
    private val reportJpaRepository: ReportJpaRepository,
) {
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun handleUserCreatedEvent(event: UserCreatedEvent) {
        reportJpaRepository.save(
            ReportEntity(
                event.userId,
            )
        )
    }
}