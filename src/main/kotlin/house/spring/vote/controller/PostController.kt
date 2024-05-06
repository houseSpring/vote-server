package house.spring.vote.controller

import house.spring.vote.domain.post.dto.*
import house.spring.vote.domain.post.model.SortBy
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.swing.SortOrder

@RestController
class PostController {
    @GetMapping("/posts")
    fun getPosts(@RequestBody query: GetPostsRequestQuery): GetPostsResponseDto {
        return GetPostsResponseDto(
            emptyList(),
            "cursor",
            query.sortBy,
            query.sortOrder
        )
    }

    @GetMapping("/posts/:id")
    fun getPost(id: String): GetPostsResponseDto {
        return GetPostsResponseDto(
            emptyList(),
            "cursor",
            SortBy.CREATED_AT,
            SortOrder.ASCENDING
        )
    }

    @GetMapping("/posts/:id/prev")
    fun getNextPostInfo(id: String, @RequestBody query: GetPrevPostRequestQuery): GetPrevPostResponseDto {
        return GetPrevPostResponseDto(
            id,
            "unReadPostId"
        )
    }

    @PostMapping("/posts")
    fun createPost(@RequestBody post: GetPostsResponseDto): String {
        return "postId"
    }

    @PostMapping("/posts/:id/pick")
    fun pickPost(id: String): CreatePickResponseDto {
        return CreatePickResponseDto(
            listOf(Long.MAX_VALUE)
        )
    }
}