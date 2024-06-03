package house.spring.vote.post.domain.model

import java.util.*

data class PostId(
    val incrementId: Long? = null,
    val uuid: String = UUID.randomUUID().toString()
)// TODO: UUID.randomUUID() 해결 필요

