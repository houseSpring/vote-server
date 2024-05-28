package house.spring.vote.infrastructure.entity

import house.spring.vote.domain.post.model.PickType
import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@Entity
@Table(
    name = "post", indexes = [
        Index(name = "idx_post_uuid", columnList = "uuid", unique = true),
        Index(name = "idx_post_cursor", columnList = "createdAt")
    ]
)
@DynamicUpdate
class PostEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false, unique = true)
    val uuid: String,
    @Column(nullable = false)
    val title: String,
    @Column(nullable = false)
    val userId: Long,
    @Column(nullable = false)
    val pickType: PickType,
    @Column()
    var imageKey: String? = null,
    @OneToMany(
        mappedBy = "post",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    val polls: List<PollEntity> = mutableListOf()
) {

    @CreatedDate
    val createdAt: LocalDateTime = LocalDateTime.now()

    @UpdateTimestamp
    val updatedAt: LocalDateTime = LocalDateTime.now()

    @Column(nullable = true)
    val deletedAt: LocalDateTime? = null

    fun addPoll(poll: PollEntity) {
        (polls as MutableList).add(poll)
        poll.post = this
    }
}