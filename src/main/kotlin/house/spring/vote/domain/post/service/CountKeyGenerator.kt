package house.spring.vote.domain.post.service

import house.spring.vote.domain.post.model.PostId

interface CountKeyGenerator {
    fun generatePickPollCountKey(pollId: Long): String
    fun generatePickPollCountKeys(pollIds: List<Long>): List<String>
    fun generatePickPostCountKey(postId: PostId): String
    fun generatePickPostCountKeys(postIds: List<PostId>): List<String>
}