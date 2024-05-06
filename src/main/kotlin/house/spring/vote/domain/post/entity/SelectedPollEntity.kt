package house.spring.vote.domain.post.entity

import jakarta.persistence.*

@Entity(name = "SelectedPoll")
class SelectedPollEntity {
    constructor(postId: Long, pollId: Long, userId: Long) {
        this.postId = postId
        this.pollId = pollId
        this.userId = userId
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(nullable = false)
    val postId: Long

    @Column(nullable = false)
    val pollId: Long

    @Column(nullable = false)
    val userId: Long
}