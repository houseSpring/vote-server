package house.spring.vote.domain.post.entity

import house.spring.vote.domain.post.model.PickType
import jakarta.persistence.*

@Entity(name = "Post")
class PostEntity {
    constructor(titleName: String, titleUrl: String?, pickType: PickType) {
        this.titleName = titleName
        this.titleUrl = titleUrl
        this.pickType = pickType
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(nullable = false)
    val titleName: String

    @Column(nullable = true)
    var titleUrl: String? = null

    @Column(nullable = false)
    val pickType: PickType

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    var polls: List<PollEntity> = mutableListOf()
}