package house.spring.vote.interfaces.controller.post

import house.spring.vote.application.post.dto.command.GenerateImageUploadUrlCommand
import house.spring.vote.application.post.dto.command.PickPostCommand
import house.spring.vote.application.post.service.PostWriteService
import house.spring.vote.domain.model.SortBy
import house.spring.vote.interfaces.controller.post.request.*
import house.spring.vote.interfaces.controller.post.response.CreatePickResponseDto
import house.spring.vote.interfaces.controller.post.response.GenerateImageUploadUrlResponseDto
import house.spring.vote.interfaces.controller.post.response.GetPostsResponseDto
import house.spring.vote.interfaces.controller.post.response.GetPrevPostResponseDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
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