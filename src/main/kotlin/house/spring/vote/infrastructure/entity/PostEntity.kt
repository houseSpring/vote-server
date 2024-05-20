package house.spring.vote.infrastructure.entity

import house.spring.vote.domain.model.PickType
import jakarta.persistence.*
import java.util.*

@Entity(name = "Post")
class PostEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false, unique = true)
    val uuid: UUID? = UUID.randomUUID(),
    @Column(nullable = false)
    val title: String,
    @Column(nullable = false)
    val userId: Long,
    @Column(nullable = false)
    val pickType: PickType,
    @Column()
    var imageUrl: String? = null,
    @OneToMany(
        mappedBy = "post",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    val polls: List<PollEntity> = mutableListOf()
) {
}