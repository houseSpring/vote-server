package house.spring.vote.domain.post.repository

import house.spring.vote.domain.post.model.PostId

interface ParticipantCountRepository {
    fun getPostIdToCountMap(postIds: List<PostId>): Map<PostId, Int>
    fun getPostCountById(postId: PostId): Int
    fun getPollIdToCountMap(ids: List<Long>): Map<Long, Int>
    fun countUpPost(postId: PostId)
    fun countUpPolls(pollIds: List<Long>)
}