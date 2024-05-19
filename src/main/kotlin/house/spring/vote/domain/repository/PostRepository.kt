package house.spring.vote.domain.repository

import house.spring.vote.infrastructure.entity.PostEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PostRepository : JpaRepository<PostEntity, Long> {
    fun findByUuid(uuid: UUID): PostEntity?
}