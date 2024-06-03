package house.spring.vote.post.application.service

import house.spring.vote.post.application.service.dto.query.GetPostsQuery
import house.spring.vote.post.application.service.dto.query.GetPrevPostIdQuery
import house.spring.vote.post.domain.model.Poll
import house.spring.vote.post.domain.model.Post
import house.spring.vote.post.application.port.repository.ParticipantCountRepository
import house.spring.vote.post.application.port.repository.dto.PostQuery
import house.spring.vote.post.application.port.repository.PostRepository
import house.spring.vote.post.application.port.ObjectManager
import house.spring.vote.post.controller.response.*
import house.spring.vote.common.domain.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class PostReadService(
    private val postRepository: PostRepository,
    private val objectManager: ObjectManager,
    private val participantCountRepository: ParticipantCountRepository,
) {
    fun getPost(postUUId: String): GetPostResponseDto {
        val post = postRepository.findByUuid(postUUId)
            ?: throw NotFoundException("게시글을 찾을 수 없습니다. ($postUUId)")

        val participantCount = participantCountRepository.getPostCountById(post.id)
        val pollIdToParticipantCount = participantCountRepository.getPollIdToCountMap(post.polls.map { it.id!! })

        return GetPostResponseDto(
            id = post.id.uuid,
            title = post.title,
            imageUrl = post.imageKey?.let { objectManager.generateDownloadUrl(it) },
            participantCount = participantCount,
            polls = post.polls.map { it.toDto(pollIdToParticipantCount[it.id]!!) },
            createdAt = post.createdAt,
            updatedAt = post.updatedAt
        )
    }


    fun getPosts(query: GetPostsQuery): GetPostsResponseDto {
        val posts = postRepository.findAllByQuery(
            PostQuery(
                userId = query.userId,
                cursor = query.cursor,
                sortBy = query.sortBy,
                sortOrder = query.sortOrder,
                pageSize = PAGE_SIZE,
            )
        )
        val postIdToParticipantCount = participantCountRepository.getPostIdToCountMap(posts.map { it.id })

        return GetPostsResponseDto(
            posts = posts.map {
                it.toDto(
                    postIdToParticipantCount[it.id]!!,
                    it.imageKey?.let { imageKey -> objectManager.generateDownloadUrl(imageKey) }
                )
            },
            cursor = posts.lastOrNull()?.id?.toString(),
            sortBy = query.sortBy,
            sortOrder = query.sortOrder
        )
    }

    fun getPrevPostIds(query: GetPrevPostIdQuery): GetPrevPostResponseDto {
        val post = postRepository.findByUuid(query.postUuid)
            ?: throw NotFoundException("게시글을 찾을 수 없습니다. ($query.postUuid)")

        val prevPosts = postRepository.findAllByQuery(
            PostQuery(
                userId = query.userId,
                sortBy = query.sortBy,
                sortOrder = query.sortOrder,
                pageSize = PAGE_SIZE,
                postId = post.id.incrementId,
            )
        )

        return GetPrevPostResponseDto(
            unReadPostIds = prevPosts.map { it.id.uuid }
        )
    }

    private fun Poll.toDto(participantCount: Int): PollResponseDto {
        return PollResponseDto(
            id = this.id!!,
            title = this.title,
            participantCount = participantCount
        )
    }

    private fun Post.toDto(participantCount: Int, imageUrl: String?): GetPostsResponseDtoPost {
        return GetPostsResponseDtoPost(
            id = this.id.uuid,
            title = this.title,
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