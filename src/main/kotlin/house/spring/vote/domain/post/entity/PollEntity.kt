package house.spring.vote.domain.post.entity

import jakarta.persistence.*

@Entity(name = "Poll")
class PollEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(nullable = false)
    val title: String? = null


    @Column(nullable = false)
    @JoinColumn
    val postId: Long? = null
}