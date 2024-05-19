package house.spring.vote.infrastructure.entity

import jakarta.persistence.*

@Entity(name = "SelectedPoll")
class PickedPollEntity(
    @Column(nullable = false)
    val postId: Long,
    @Column(nullable = false)
    val pollId: Long,
    @Column(nullable = false)
    val userId: Long
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

}