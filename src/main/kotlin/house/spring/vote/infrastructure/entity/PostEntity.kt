package house.spring.vote.infrastructure.entity

import house.spring.vote.domain.model.PickType
import jakarta.persistence.*
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "post", indexes = [Index(name = "idx_post_uuid", columnList = "uuid", unique = true)])
class PostEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false, unique = true)
    val uuid: UUID? = UUID.randomUUID(), // TODO: UUID.randomUUID() 해결 필요
    @Column(nullable = false)
    val title: String,
    @Column(nullable = false)
    val userId: Long,
    @Column(nullable = false)
    val pickType: PickType,
    @Column()
    var imageUrl: String? = null,
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

    val deletedAt: LocalDateTime? = null
}