package house.spring.vote.post.controller

import house.spring.vote.common.controller.annotation.CurrentUser
import house.spring.vote.common.controller.annotation.SecureEndPoint
import house.spring.vote.post.application.port.`in`.PostCommandService
import house.spring.vote.post.application.port.`in`.PostQueryService
import house.spring.vote.post.application.service.dto.command.GenerateImageUploadUrlCommand
import house.spring.vote.post.application.service.dto.command.PickPostCommand
import house.spring.vote.post.application.service.dto.query.GetPostsQuery
import house.spring.vote.post.controller.request.CreatePostRequestDto
import house.spring.vote.post.controller.request.CreatePostResponseDto
import house.spring.vote.post.controller.request.GetPostsRequestDto
import house.spring.vote.post.controller.request.PickPostRequestDto
import house.spring.vote.post.controller.response.CreatePickResponseDto
import house.spring.vote.post.controller.response.GenerateImageUploadUrlResponseDto
import house.spring.vote.post.controller.response.GetPostResponseDto
import house.spring.vote.post.controller.response.GetPostsResponseDto
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import house.spring.vote.common.domain.CurrentUser as LoginUser


@RestController
class PostController(
    private val postCommandService: PostCommandService, private val postQueryService: PostQueryService,
) {

    @SecureEndPoint
    @PostMapping("/upload-url")
    suspend fun generateImageUploadUrl(
        @CurrentUser user: LoginUser,
    ): ResponseEntity<GenerateImageUploadUrlResponseDto> {
        val command = GenerateImageUploadUrlCommand(user.id)
        val result = this.postCommandService.createImageUploadUrl(command)
        val responseDto = GenerateImageUploadUrlResponseDto(result.uploadUrl, result.imageKey)
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto)
    }

    @SecureEndPoint
    @PostMapping("/posts")
    suspend fun createPost(
        @Valid @RequestBody dto: CreatePostRequestDto,
        @CurrentUser user: LoginUser,
    ): ResponseEntity<CreatePostResponseDto> {
        val command = dto.toCommand(user.id)
        val createdId = this.postCommandService.create(command)
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
        val result = this.postCommandService.pickPost(command)
        val responseDto = CreatePickResponseDto.from(result)
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto)
    }


    @SecureEndPoint
    @GetMapping("/posts")
    fun getPosts(
        @Valid @ModelAttribute dto: GetPostsRequestDto,
        @CurrentUser user: LoginUser,
    ): ResponseEntity<GetPostsResponseDto> {
        val query = GetPostsQuery(
            offset = dto.offset,
            sortBy = dto.sortBy,
            sortOrder = dto.sortOrder,
            userId = user.id,
        )
        val result = postQueryService.getPosts(query)
        return ResponseEntity.ok(result)
    }

    @SecureEndPoint
    @GetMapping("/posts/{id}")
    fun getPost(@PathVariable id: String): ResponseEntity<GetPostResponseDto> {
        val result = postQueryService.getPost(id)
        return ResponseEntity.ok(result)
    }
}