package house.spring.vote.post.infrastructure.entity

import house.spring.vote.post.domain.model.PickType
import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.domain.AbstractAggregateRoot
import org.springframework.data.domain.AfterDomainEventPublication
import org.springframework.data.domain.DomainEvents
import java.time.LocalDateTime

@Entity
@Table(
    name = "post", indexes = [
        Index(name = "idx_post_cursor", columnList = "createdAt")
    ]
)
@DynamicUpdate
class PostEntity(
    @Id
    val id: String,
    @Column(nullable = false)
    val title: String,
    @Column(nullable = false)
    val userId: String,
    @Column(nullable = false)
    val pickType: PickType,
    @Column(nullable = true)
    var imageKey: String? = null,
    @OneToMany(
        mappedBy = "post",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    val polls: MutableList<PollEntity> = mutableListOf(),
) : AbstractAggregateRoot<PostEntity>() {

    @CreatedDate
    val createdAt: LocalDateTime = LocalDateTime.now()

    @UpdateTimestamp
    val updatedAt: LocalDateTime = LocalDateTime.now()

    @Column(nullable = true)
    val deletedAt: LocalDateTime? = null

    @DomainEvents
    override fun domainEvents(): Collection<Any> {
        return super.domainEvents()
    }

    @AfterDomainEventPublication
    override fun clearDomainEvents() {
        super.clearDomainEvents()
    }

    fun addPoll(poll: PollEntity) {
        polls.add(poll)
        poll.post = this
    }

    fun addEvents(events: List<Any>) {
        registerEvent(events)
    }
}