package house.spring.vote.post.application.port.`in`

import house.spring.vote.post.application.service.dto.query.GetPostsQuery
import house.spring.vote.post.application.service.dto.query.GetPrevPostIdQuery
import house.spring.vote.post.controller.response.GetPostResponseDto
import house.spring.vote.post.controller.response.GetPostsResponseDto
import house.spring.vote.post.controller.response.GetPrevPostResponseDto

interface PostQueryService {
    fun getPost(postId:String):GetPostResponseDto
    fun getPosts(query: GetPostsQuery):GetPostsResponseDto
    fun getPrevPostIds(query:GetPrevPostIdQuery):GetPrevPostResponseDto
}