package house.spring.vote.post.infrastructure.repository

import house.spring.vote.post.infrastructure.entity.PostEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface PostJpaRepository : JpaRepository<PostEntity, String> {
    @Query("SELECT p FROM PostEntity AS p LEFT JOIN PickedPollEntity AS pp ON p.id = pp.postId AND pp.userId = :userId WHERE p.id < :cursor AND p.deletedAt IS NULL")
    fun findAllByIdSmallerThanCursor(
        @Param("cursor") cursor: Long,
        @Param("userId") userId: String,
        pageable: Pageable
    ): ArrayList<PostEntity>

    @Query("SELECT p FROM PostEntity p  LEFT JOIN PickedPollEntity AS pp ON p.id = pp.postId AND pp.userId = :userId WHERE p.id > :cursor AND p.deletedAt IS NULL")
    fun findAllByIdBiggerThanCursor(
        @Param("cursor") cursor: Long,
        @Param("userId") userId: String,
        pageable: Pageable
    ): ArrayList<PostEntity>

}