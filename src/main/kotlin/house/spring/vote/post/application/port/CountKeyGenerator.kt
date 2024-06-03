package house.spring.vote.post.application.port

import house.spring.vote.post.domain.model.PostId

interface CountKeyGenerator {
    fun generatePickPollCountKey(pollId: Long): String
    fun generatePickPollCountKeys(pollIds: List<Long>): List<String>
    fun generatePickPostCountKey(postId: PostId): String
    fun generatePickPostCountKeys(postIds: List<PostId>): List<String>
}