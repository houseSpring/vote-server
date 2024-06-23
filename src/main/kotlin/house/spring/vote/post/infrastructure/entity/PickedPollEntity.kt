package house.spring.vote.post.infrastructure.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@Entity()
@Table(
    name = "picked_poll",
    indexes = [
        Index(name = "idx_user_post", columnList = "userId, postId"),
    ]
)
class PickedPollEntity(
    @Id
    val id: String,
    @Column(nullable = false)
    val postId: String,
    @Column(nullable = false)
    val pollId: String,
    @Column(nullable = false)
    val userId: String,
) {
    @CreatedDate
    val createdAt: LocalDateTime = LocalDateTime.now()
}