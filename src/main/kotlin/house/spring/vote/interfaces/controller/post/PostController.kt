package house.spring.vote.interfaces.controller.post

import house.spring.vote.application.post.dto.command.GenerateImageUploadUrlCommand
import house.spring.vote.application.post.dto.command.PickPostCommand
import house.spring.vote.application.post.dto.query.GetPostsQuery
import house.spring.vote.application.post.dto.query.GetPrevPostIdQuery
import house.spring.vote.application.post.service.PostReadService
import house.spring.vote.application.post.service.PostWriteService
import house.spring.vote.interfaces.controller.post.request.*
import house.spring.vote.interfaces.controller.post.response.*
import house.spring.vote.util.annotation.SecureEndPoint
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*


@RestController
class PostController(
    private val postWriteService: PostWriteService, private val postReadService: PostReadService
) {

    @SecureEndPoint
    @PostMapping("/upload-url")
    suspend fun generateImageUploadUrl(
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<GenerateImageUploadUrlResponseDto> {
        val command = GenerateImageUploadUrlCommand(userDetails.username.toLong())
        val result = this.postWriteService.createImageUploadUrl(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(result)
    }

    @SecureEndPoint
    @PostMapping("/posts")
    suspend fun createPost(
        @Valid @RequestBody dto: CreatePostRequestDto,
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<CreatePostResponseDto> {
        val command = dto.toCommand(userDetails.username.toLong())
        val result = this.postWriteService.create(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(CreatePostResponseDto(result))
    }

    @SecureEndPoint
    @PostMapping("/posts/{id}/pick")
    fun pickPost(
        @PathVariable("id") postId: String,
        @Valid @RequestBody dto: PickPostRequestDto,
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<CreatePickResponseDto> {
        val command = PickPostCommand(postId, userDetails.username.toLong(), dto.pickedPollIds)
        val result = this.postWriteService.pickPost(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(result)
    }


    @SecureEndPoint
    @GetMapping("/posts")
    fun getPosts(
        @Valid @ModelAttribute dto: GetPostsRequestDto,
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<GetPostsResponseDto> {
        val query = GetPostsQuery(
            cursor = dto.cursor, sortBy = dto.sortBy, sortOrder = dto.sortOrder, userId = userDetails.username.toLong()
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
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<GetPrevPostResponseDto> {
        val query = GetPrevPostIdQuery(
            userId = userDetails.username.toLong(), postUuid = postId, sortBy = dto.sortBy, sortOrder = dto.sortOrder
        )
        val result = postReadService.getPrevPostIds(query)
        return ResponseEntity.ok(result)
    }
}