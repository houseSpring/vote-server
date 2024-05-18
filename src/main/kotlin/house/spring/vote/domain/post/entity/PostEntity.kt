package house.spring.vote.domain.post.entity

import house.spring.vote.domain.post.model.PickType
import jakarta.persistence.*
import java.util.UUID

@Entity(name = "Post")
class PostEntity(
    @Column(nullable = false)
    val title: String,
    @Column(nullable = false)
    val userId: Long,
    @Column(nullable = false)
    val pickType: PickType,
    @Column()
    var imageUrl: String?
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(nullable = false, unique = true)
    val uuid: UUID = UUID.randomUUID()

    @OneToMany(
        mappedBy = "post",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    val polls: MutableList<PollEntity> = mutableListOf()
}