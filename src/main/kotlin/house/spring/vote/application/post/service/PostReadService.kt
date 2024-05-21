package house.spring.vote.application.post.service

import house.spring.vote.application.post.dto.query.GetPostsQuery
import house.spring.vote.interfaces.controller.post.response.*

interface PostReadService {
    fun getPosts(query: GetPostsQuery): GetPostsResponseDto
    fun getPost(postId: String): GetPostResponseDto
}