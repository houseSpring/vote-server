package house.spring.vote.domain.post.entity

import jakarta.persistence.*

@Entity(name = "Poll")
class PollEntity {
    constructor(
        title: String,
        postId: Long,
        userId: Long
    ) {
        this.title = title
        this.postId = postId
        this.userId = userId
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(nullable = false)
    val title: String

    @Column(nullable = false)
    @JoinColumn
    val postId: Long

    @Column(nullable = false)
    val userId: Long
}