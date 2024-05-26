package house.spring.vote.application.post.service

import house.spring.vote.application.post.dto.query.GetPostsQuery
import house.spring.vote.application.post.dto.query.GetPrevPostIdQuery
import house.spring.vote.interfaces.controller.post.response.GetPostResponseDto
import house.spring.vote.interfaces.controller.post.response.GetPostsResponseDto
import house.spring.vote.interfaces.controller.post.response.GetPrevPostResponseDto

interface PostReadService {
    fun getPosts(query: GetPostsQuery): GetPostsResponseDto
    fun getPost(postUUId: String): GetPostResponseDto
    fun getPrevPostIds(query: GetPrevPostIdQuery): GetPrevPostResponseDto
}