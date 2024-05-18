package house.spring.vote.domain.post.repository

import house.spring.vote.domain.post.entity.PostEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository: JpaRepository<PostEntity, Long> {
}