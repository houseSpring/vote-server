package house.spring.vote.domain.post.entity

import jakarta.persistence.*

@Entity(name = "Poll")
class PollEntity(
        @Column(nullable = false)
        val title: String,
        @Column(nullable = false)
        @JoinColumn
        val postId: Long,
        @Column(nullable = false)
        val userId: Long
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

}