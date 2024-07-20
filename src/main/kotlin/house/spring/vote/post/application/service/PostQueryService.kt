package house.spring.vote.post.application.service

import house.spring.vote.common.domain.exception.not_found.PostNotFoundException
import house.spring.vote.post.application.port.ObjectManager
import house.spring.vote.post.application.port.repository.ParticipantCountRepository
import house.spring.vote.post.application.port.repository.PostRepository
import house.spring.vote.post.application.port.repository.dto.PostQuery
import house.spring.vote.post.application.service.dto.query.GetPostsQuery
import house.spring.vote.post.application.service.dto.query.GetPrevPostIdQuery
import house.spring.vote.post.controller.response.GetPostResponseDto
import house.spring.vote.post.controller.response.GetPostsResponseDto
import house.spring.vote.post.controller.response.GetPrevPostResponseDto
import house.spring.vote.post.infrastructure.entity.PollEntity
import house.spring.vote.post.infrastructure.entity.PostEntity
import org.springframework.stereotype.Service

@Service
class PostQueryService(
    private val postRepository: PostRepository,
    private val objectManager: ObjectManager,
    private val participantCountRepository: ParticipantCountRepository,
) {
    fun getPost(postId: String): GetPostResponseDto {
        val post = postRepository.findEntityById(postId)
            ?: throw PostNotFoundException(" (postId: $postId)")

        val idToCount = participantCountRepository.getPostCount(postId)
        return GetPostResponseDto(
            id = post.id,
            title = post.title,
            imageUrl = post.imageKey?.let { objectManager.generateDownloadUrl(it) },
            participantCount = idToCount[postId] ?: 0,
            polls = post.polls.map { it.toDto(idToCount[it.id] ?: 0) },
            createdAt = post.createdAt,
            updatedAt = post.updatedAt
        )
    }


    fun getPosts(query: GetPostsQuery): GetPostsResponseDto {
        val page = postRepository.findAllByQuery(
            PostQuery(
                userId = query.userId,
                offset = query.offset,
                sortBy = query.sortBy,
                sortOrder = query.sortOrder,
                pageSize = PAGE_SIZE,
            )
        )
        val postIdToCount = participantCountRepository.getPostsCount(page.content.map { it.id })
        return GetPostsResponseDto(
            posts = page.content.map {
                it.toDto(
                    postIdToCount[it.id]!!,
                    it.imageKey?.let { imageKey -> objectManager.generateDownloadUrl(imageKey) }
                )
            },
            currentPage = page.number,
            totalPages = page.totalPages,
            sortBy = query.sortBy,
            sortOrder = query.sortOrder
        )
    }

    fun getPrevPostIds(query: GetPrevPostIdQuery): GetPrevPostResponseDto {
        // TODO: 페이지네이션으로 변경으로 의도대로 동작하지 않음
        val page = postRepository.findAllByQuery(
            PostQuery(
                userId = query.userId,
                sortBy = query.sortBy,
                sortOrder = query.sortOrder,
                pageSize = PAGE_SIZE,
            )
        )

        return GetPrevPostResponseDto(
            unReadPostIds = page.content.map { it.id }
        )
    }

    private fun PollEntity.toDto(participantCount: Int): GetPostResponseDto.PollResponseDto {
        return GetPostResponseDto.PollResponseDto(
            id = this.id,
            title = this.title,
            participantCount = participantCount
        )
    }

    private fun PostEntity.toDto(
        participantCount: Int,
        imageUrl: String?,
    ): GetPostsResponseDto.GetPostsResponseDtoPost {
        return GetPostsResponseDto.GetPostsResponseDtoPost(
            id = this.id,
            title = this.title,
            userId = this.userId,
            imageUrl = imageUrl,
            participantCount = participantCount,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt
        )
    }


    companion object {
        private const val PAGE_SIZE = 20
    }
}
