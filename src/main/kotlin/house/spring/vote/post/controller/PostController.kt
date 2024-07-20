package house.spring.vote.post.controller

import house.spring.vote.common.controller.annotation.CurrentUser
import house.spring.vote.common.controller.annotation.SecureEndPoint
import house.spring.vote.post.application.service.PostReadService
import house.spring.vote.post.application.service.PostWriteService
import house.spring.vote.post.application.service.dto.command.GenerateImageUploadUrlCommand
import house.spring.vote.post.application.service.dto.command.PickPostCommand
import house.spring.vote.post.application.service.dto.query.GetPostsQuery
import house.spring.vote.post.application.service.dto.query.GetPrevPostIdQuery
import house.spring.vote.post.controller.request.*
import house.spring.vote.post.controller.response.*
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import house.spring.vote.common.domain.CurrentUser as LoginUser


@RestController
class PostController(
    private val postWriteService: PostWriteService, private val postReadService: PostReadService,
) {

    @SecureEndPoint
    @PostMapping("/upload-url")
    suspend fun generateImageUploadUrl(
        @CurrentUser user: LoginUser,
    ): ResponseEntity<GenerateImageUploadUrlResponseDto> {
        println("user: $user")
        val command = GenerateImageUploadUrlCommand(user.id)
        val result = this.postWriteService.createImageUploadUrl(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(result)
    }

    @SecureEndPoint
    @PostMapping("/posts")
    suspend fun createPost(
        @Valid @RequestBody dto: CreatePostRequestDto,
        @CurrentUser user: LoginUser,
    ): ResponseEntity<CreatePostResponseDto> {
        val command = dto.toCommand(user.id)
        val createdId = this.postWriteService.create(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(CreatePostResponseDto(createdId))
    }

    @SecureEndPoint
    @PostMapping("/posts/{id}/pick")
    fun pickPost(
        @PathVariable("id") postId: String,
        @Valid @RequestBody dto: PickPostRequestDto,
        @CurrentUser user: LoginUser,
    ): ResponseEntity<CreatePickResponseDto> {
        val command = PickPostCommand(postId, user.id, dto.pickedPollIds)
        val result = this.postWriteService.pickPost(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(result)
    }


    @SecureEndPoint
    @GetMapping("/posts")
    fun getPosts(
        @Valid @ModelAttribute dto: GetPostsRequestDto,
        @CurrentUser user: LoginUser,
    ): ResponseEntity<GetPostsResponseDto> {
        val query = GetPostsQuery(
            cursor = dto.cursor, sortBy = dto.sortBy, sortOrder = dto.sortOrder, userId = user.id,
        )
        val result = postReadService.getPosts(query)
        return ResponseEntity.ok(result)
    }

    @SecureEndPoint
    @GetMapping("/posts/{id}")
    fun getPost(@PathVariable id: String): ResponseEntity<GetPostResponseDto> {
        val result = postReadService.getPost(id)
        return ResponseEntity.ok(result)
    }

    @SecureEndPoint
    @GetMapping("/posts/{id}/prev")
    fun getNextPostInfo(
        @PathVariable("id") postId: String,
        @ModelAttribute dto: GetPrevPostRequestDto,
        @CurrentUser user: LoginUser,
    ): ResponseEntity<GetPrevPostResponseDto> {
        val query = GetPrevPostIdQuery(
            userId = user.id, postId = postId, sortBy = dto.sortBy, sortOrder = dto.sortOrder
        )
        val result = postReadService.getPrevPostIds(query)
        return ResponseEntity.ok(result)
    }
}