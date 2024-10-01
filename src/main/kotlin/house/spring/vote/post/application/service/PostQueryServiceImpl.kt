package house.spring.vote.post.application.service

import house.spring.vote.common.domain.exception.not_found.PostNotFoundException
import house.spring.vote.post.application.port.`in`.PostQueryService
import house.spring.vote.post.application.port.out.ObjectManager
import house.spring.vote.post.application.port.out.repository.ParticipantCountRepository
import house.spring.vote.post.application.port.out.repository.PickedPollRepository
import house.spring.vote.post.application.port.out.repository.PostRepository
import house.spring.vote.post.application.port.out.repository.dto.PostQuery
import house.spring.vote.post.application.service.dto.query.GetPostsQuery
import house.spring.vote.post.application.service.dto.query.GetPrevPostIdQuery
import house.spring.vote.post.controller.response.GetPostResponseDto
import house.spring.vote.post.controller.response.GetPostsResponseDto
import house.spring.vote.post.controller.response.GetPrevPostResponseDto
import house.spring.vote.post.infrastructure.entity.PollEntity
import house.spring.vote.post.infrastructure.entity.PostEntity
import org.springframework.stereotype.Service

@Service
class PostQueryServiceImpl(
    private val postRepository: PostRepository,
    private val objectManager: ObjectManager,
    private val participantCountRepository: ParticipantCountRepository,
    private val prickedPollRepository: PickedPollRepository,
) : PostQueryService {
    override fun getPost(postId: String): GetPostResponseDto {
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

    override fun getPosts(query: GetPostsQuery): GetPostsResponseDto {
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
        val pickedPollSet = prickedPollRepository.findAllByUserIdAndPostIds(
            userId = query.userId,
            postIds = page.content.map { it.id }
        ).map { it.postId }.toHashSet()

        return GetPostsResponseDto(
            posts = page.content.map {
                it.toDto(
                    participantCount = postIdToCount[it.id]!!,
                    imageUrl = it.imageKey?.let { imageKey -> objectManager.generateDownloadUrl(imageKey) },
                    isVoted = pickedPollSet.contains(it.id)
                )
            },
            currentPage = page.number,
            totalPages = page.totalPages,
            sortBy = query.sortBy,
            sortOrder = query.sortOrder
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
        isVoted: Boolean,
    ): GetPostsResponseDto.GetPostsResponseDtoPost {
        return GetPostsResponseDto.GetPostsResponseDtoPost(
            id = this.id,
            title = this.title,
            userId = this.userId,
            imageUrl = imageUrl,
            participantCount = participantCount,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
            isVoted = isVoted
        )
    }


    companion object {
        private const val PAGE_SIZE = 20
    }
}
