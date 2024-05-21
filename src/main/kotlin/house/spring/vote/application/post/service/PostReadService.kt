package house.spring.vote.application.post.service

import house.spring.vote.interfaces.controller.post.response.*

interface PostReadService {
//    fun getPosts(postId: String): String
    fun getPost(postUUId: String): GetPostResponseDto
}