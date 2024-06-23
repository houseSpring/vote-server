package house.spring.vote.post.controller

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
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*


@RestController
class PostController(
    private val postWriteService: PostWriteService, private val postReadService: PostReadService,
) {

    @SecureEndPoint
    @PostMapping("/upload-url")
    suspend fun generateImageUploadUrl(
        @AuthenticationPrincipal userDetails: UserDetails,
    ): ResponseEntity<GenerateImageUploadUrlResponseDto> {
        val command = GenerateImageUploadUrlCommand(userDetails.username)
        val result = this.postWriteService.createImageUploadUrl(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(result)
    }

    @SecureEndPoint
    @PostMapping("/posts")
    suspend fun createPost(
        @Valid @RequestBody dto: CreatePostRequestDto,
        @AuthenticationPrincipal userDetails: UserDetails,
    ): ResponseEntity<CreatePostResponseDto> {
        val command = dto.toCommand(userDetails.username)
        val createdId = this.postWriteService.create(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(CreatePostResponseDto(createdId))
    }

    @SecureEndPoint
    @PostMapping("/posts/{id}/pick")
    fun pickPost(
        @PathVariable("id") postId: String,
        @Valid @RequestBody dto: PickPostRequestDto,
        @AuthenticationPrincipal userDetails: UserDetails,
    ): ResponseEntity<CreatePickResponseDto> {
        val command = PickPostCommand(postId, userDetails.username, dto.pickedPollIds)
        val result = this.postWriteService.pickPost(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(result)
    }


    @SecureEndPoint
    @GetMapping("/posts")
    fun getPosts(
        @Valid @ModelAttribute dto: GetPostsRequestDto,
        @AuthenticationPrincipal userDetails: UserDetails,
    ): ResponseEntity<GetPostsResponseDto> {
        val query = GetPostsQuery(
            cursor = dto.cursor, sortBy = dto.sortBy, sortOrder = dto.sortOrder, userId = userDetails.username,
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
        @AuthenticationPrincipal userDetails: UserDetails,
    ): ResponseEntity<GetPrevPostResponseDto> {
        val query = GetPrevPostIdQuery(
            userId = userDetails.username, postId = postId, sortBy = dto.sortBy, sortOrder = dto.sortOrder
        )
        val result = postReadService.getPrevPostIds(query)
        return ResponseEntity.ok(result)
    }
}