package house.spring.vote.domain.post.repository

import house.spring.vote.infrastructure.entity.PollEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PollRepository : JpaRepository<PollEntity, Long>