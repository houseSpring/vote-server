package house.spring.vote.application.post.service

import house.spring.vote.application.post.dto.query.GetPostsQuery
import house.spring.vote.interfaces.controller.post.response.GetPostResponseDto
import house.spring.vote.interfaces.controller.post.response.GetPostsResponseDto

interface PostReadService {
    fun getPosts(query: GetPostsQuery): GetPostsResponseDto
    fun getPost(postUUId: String): GetPostResponseDto
}