package house.spring.vote.interfaces.controller.post

import house.spring.vote.application.post.dto.command.GenerateImageUploadUrlCommand
import house.spring.vote.application.post.dto.command.PickPostCommand
import house.spring.vote.application.post.dto.query.GetPostsQuery
import house.spring.vote.application.post.service.PostReadService
import house.spring.vote.application.post.service.PostWriteService
import house.spring.vote.interfaces.controller.post.request.*
import house.spring.vote.interfaces.controller.post.response.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
class PostController(
    private val postWriteService: PostWriteService,
    private val postReadService: PostReadService
) {

    // TODO: userAuthN 에서 받아오는 userId로 변경
    private val userId = 1L

    @PostMapping("/upload-url")
    suspend fun generateImageUploadUrl(): ResponseEntity<GenerateImageUploadUrlResponseDto> {
        val command = GenerateImageUploadUrlCommand(userId)
        val result = this.postWriteService.createImageUploadUrl(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(result)
    }

    @PostMapping("/posts")
    suspend fun createPost(@RequestBody dto: CreatePostRequestDto): ResponseEntity<CreatePostResponseDto> {
        val command = dto.toCommand(userId)
        val result = this.postWriteService.create(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(CreatePostResponseDto(result))
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
    fun getPosts(@RequestBody dto: GetPostsRequestDto): ResponseEntity<GetPostsResponseDto> {
        val query = GetPostsQuery(
            cursor = dto.cursor,
            sortBy = dto.sortBy,
            sortOrder = dto.sortOrder
        )
        val result = postReadService.getPosts(query)
        return ResponseEntity.ok(result)
    }

    @GetMapping("/posts/{id}")
    fun getPost(@PathVariable id: String): ResponseEntity<GetPostResponseDto> {
        val result = postReadService.getPost(id)
        return ResponseEntity.ok(result)
    }

    // TODO: 이미 투표한 컨텐츠는 보여주지 않음
    @GetMapping("/posts/{id}/prev")
    fun getNextPostInfo(
        @PathVariable id: String,
        @RequestBody query: GetPrevPostRequestQuery
    ): GetPrevPostResponseDto {
        return GetPrevPostResponseDto(
            id,
            "unReadPostId"
        )
    }
}