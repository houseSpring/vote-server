package house.spring.vote.infrastructure.post.entity

import jakarta.persistence.*

@Entity(name = "Poll")
class PollEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false)
    val title: String,
    @Column(nullable = false, insertable = false, updatable = false)
    val postId: Long? = null
) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    var post: PostEntity? = null
}