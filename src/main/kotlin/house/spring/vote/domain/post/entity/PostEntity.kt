package house.spring.vote.domain.post.entity

import house.spring.vote.domain.post.model.PickType
import jakarta.persistence.*

@Entity(name = "Post")
class PostEntity(
        @Column(nullable = false)
        val titleName: String,
        @Column(nullable = true)
        var titleUrl: String?,
        @Column(nullable = false)
        val pickType: PickType
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    var polls: List<PollEntity> = mutableListOf()
}