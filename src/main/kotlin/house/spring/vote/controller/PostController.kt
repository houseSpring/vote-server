package house.spring.vote.controller

import house.spring.vote.domain.post.dtos.command.CreatePostCommand
import house.spring.vote.domain.post.dtos.command.GenerateImageUploadUrlCommand
import house.spring.vote.domain.post.dtos.command.PickPostCommand
import house.spring.vote.domain.post.dtos.request.*
import house.spring.vote.domain.post.dtos.response.CreatePickResponseDto
import house.spring.vote.domain.post.dtos.response.GenerateImageUploadUrlResponseDto
import house.spring.vote.domain.post.dtos.response.GetPostsResponseDto
import house.spring.vote.domain.post.dtos.response.GetPrevPostResponseDto
import house.spring.vote.domain.post.model.SortBy
import house.spring.vote.domain.post.service.PostWriteService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.swing.SortOrder

@RestController
class PostController(private val postWriteService: PostWriteService) {

    private val userId = 1L

    @PostMapping("/upload-url")
    suspend fun generateImageUploadUrl(): ResponseEntity<GenerateImageUploadUrlResponseDto> {
        val command = GenerateImageUploadUrlCommand(userId)
        val result = this.postWriteService.createImageUploadUrl(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(result)
    }

    @PostMapping("/posts")
    suspend fun createPost(@RequestBody dto: CreatePostRequestDto): ResponseEntity<String> {
        val command = dto.toCommand(userId)
        val result = this.postWriteService.create(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(result)
    }

    @PostMapping("/posts/{id}/pick")
    fun pickPost(
        @PathVariable("id") postId: String,
        @RequestBody dto: PickPostRequestDto
    ): ResponseEntity<CreatePickResponseDto> {
        val command = PickPostCommand(postId, userId, dto.pickedPollIds)
        val result = this.postWriteService.pickPost(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(result)
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

    @GetMapping("/posts/{id}")
    fun getPost(@PathVariable id: String): GetPostsResponseDto {
        return GetPostsResponseDto(
            emptyList(),
            "cursor",
            SortBy.CREATED_AT,
            SortOrder.ASCENDING
        )
    }

    @GetMapping("/posts/{id}/prev")
    fun getNextPostInfo(@PathVariable id: String, @RequestBody query: GetPrevPostRequestQuery): GetPrevPostResponseDto {
        return GetPrevPostResponseDto(
            id,
            "unReadPostId"
        )
    }
}