package house.spring.vote.post.application.port.repository

import house.spring.vote.post.domain.model.PostId

interface ParticipantCountRepository {
    fun getPostIdToCountMap(postIds: List<PostId>): Map<PostId, Int>
    fun getPostCountById(postId: PostId): Int
    fun getPollIdToCountMap(ids: List<Long>): Map<Long, Int>
    fun countUpPost(postId: PostId)
    fun countUpPolls(pollIds: List<Long>)
}