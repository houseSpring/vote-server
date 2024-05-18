package house.spring.vote.domain.post.entity

import jakarta.persistence.*

@Entity(name = "Poll")
class PollEntity(
    @Column(nullable = false)
    val title: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    var post: PostEntity? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(nullable = false, insertable = false, updatable = false)
    val postId: Long?=  null
}