package house.spring.vote.domain.post.repository

import house.spring.vote.domain.post.entity.PostEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PostRepository : JpaRepository<PostEntity, Long> {
    fun findByUuid(uuid: UUID): PostEntity?
}