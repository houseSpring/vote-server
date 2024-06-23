package house.spring.vote.post.infrastructure.entity

import jakarta.persistence.*

@Entity(name = "Poll")
class PollEntity(
    @Id
    val id: String,
    @Column(nullable = false)
    val title: String,
    @Column(nullable = false, insertable = false, updatable = false)
    val postId: String,
) {
    @ManyToOne()
    @JoinColumn(name = "postId")
    var post: PostEntity? = null
}