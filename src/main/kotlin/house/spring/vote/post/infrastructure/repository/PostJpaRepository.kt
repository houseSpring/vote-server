package house.spring.vote.post.infrastructure.repository

import house.spring.vote.post.infrastructure.entity.PostEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PostJpaRepository : JpaRepository<PostEntity, String>