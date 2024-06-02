package house.spring.vote.infrastructure.post.entity

import jakarta.persistence.*

@Entity()
@Table(
    name = "picked_poll",
    indexes = [
        Index(name = "idx_user_post", columnList = "userId, postId"),
    ]
)
class PickedPollEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false)
    val postId: Long,
    @Column(nullable = false)
    val pollId: Long,
    @Column(nullable = false)
    val userId: Long
) {

}