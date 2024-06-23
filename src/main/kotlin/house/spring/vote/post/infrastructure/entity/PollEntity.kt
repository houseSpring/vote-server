package house.spring.vote.post.infrastructure.entity

import jakarta.persistence.*

@Entity
@Table(name = "poll")
class PollEntity(
    @Id
    val id: String,
    @Column(nullable = false)
    val title: String,
) {
    @ManyToOne(
        fetch = FetchType.LAZY,
        optional = false
    )
    @JoinColumn(name = "post_id")
    var post: PostEntity? = null
}