package house.spring.vote.controller

import house.spring.vote.domain.post.dtos.command.GenerateImageUploadUrlCommand
import house.spring.vote.domain.post.dtos.request.CreatePostRequestDto
import house.spring.vote.domain.post.dtos.request.GetPostsRequestQuery
import house.spring.vote.domain.post.dtos.request.GetPrevPostRequestQuery
import house.spring.vote.domain.post.dtos.response.CreatePickResponseDto
import house.spring.vote.domain.post.dtos.response.GenerateImageUploadUrlResponseDto
import house.spring.vote.domain.post.dtos.response.GetPostsResponseDto
import house.spring.vote.domain.post.dtos.response.GetPrevPostResponseDto
import house.spring.vote.domain.post.model.SortBy
import house.spring.vote.domain.post.service.PostWriteService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.swing.SortOrder

@RestController
class PostController(private val postWriteService: PostWriteService) {

    @PostMapping("/upload-url")
    suspend fun generateImageUploadUrl(): GenerateImageUploadUrlResponseDto {
        return this.postWriteService.createImageUploadUrl(GenerateImageUploadUrlCommand(1))
    }

    @PostMapping("/posts")
    suspend fun createPost(@RequestBody body: CreatePostRequestDto): ResponseEntity<String> {
        val result = this.postWriteService.create(body.toCommand(1))
        return ResponseEntity.status(HttpStatus.CREATED).body(result)
    }

    @PostMapping("/posts/:id/pick")
    fun pickPost(id: String): CreatePickResponseDto {
        return CreatePickResponseDto(
            listOf(Long.MAX_VALUE)
        )
    }


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
}