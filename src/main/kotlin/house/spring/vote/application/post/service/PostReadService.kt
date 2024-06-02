package house.spring.vote.application.post.service

import house.spring.vote.application.post.dto.query.GetPostsQuery
import house.spring.vote.application.post.dto.query.GetPrevPostIdQuery
import house.spring.vote.domain.post.repository.ParticipantCountRepository
import house.spring.vote.domain.post.repository.PostRepository
import house.spring.vote.domain.post.service.ObjectManager
import house.spring.vote.infrastructure.post.entity.PollEntity
import house.spring.vote.infrastructure.post.entity.PostEntity
import house.spring.vote.interfaces.controller.post.response.*
import house.spring.vote.util.excaption.NotFoundException
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import javax.swing.SortOrder

@Service
class PostReadService(
    private val postRepository: PostRepository,
    private val objectManager: ObjectManager,
    private val participantCountRepository: ParticipantCountRepository,
) {
    fun getPost(postUUId: String): GetPostResponseDto {
        val postEntity = postRepository.findByUuid(postUUId)
            ?: throw NotFoundException("게시글을 찾을 수 없습니다. ($postUUId)")

        val participantCount = participantCountRepository.getPostCountById(postEntity.id!!)
        val pollIdToParticipantCount = participantCountRepository.getPollIdToCountMap(postEntity.polls.map { it.id!! })

        return GetPostResponseDto(
            id = postEntity.uuid,
            title = postEntity.title,
            imageUrl = postEntity.imageKey?.let { objectManager.generateDownloadUrl(it) },
            participantCount = participantCount,
            polls = postEntity.polls.map { it.toDto(pollIdToParticipantCount[it.id]!!) },
            createdAt = postEntity.createdAt,
            updatedAt = postEntity.updatedAt
        )
    }


    fun getPosts(query: GetPostsQuery): GetPostsResponseDto {
        val sort = idSortOrderToSort(query.sortOrder)
        val pageable = PageRequest.of(0, PAGE_SIZE, sort)

        val cursor = query.cursor?.toLong() ?: if (query.sortOrder == SortOrder.DESCENDING) Long.MAX_VALUE else 0
        val posts = if (query.sortOrder == SortOrder.DESCENDING) {
            postRepository.findAllByIdSmallerThanCursor(cursor, query.userId, pageable)
        } else {
            postRepository.findAllByIdBiggerThanCursor(cursor, query.userId, pageable)
        }

        val postIdToParticipantCount = participantCountRepository.getPostIdToCountMap(posts.map { it.id!! })

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

        val sort = idSortOrderToSort(query.sortOrder)
        val pageable = PageRequest.of(0, PAGE_SIZE, sort)
        val prevPost = if (query.sortOrder == SortOrder.DESCENDING) {
            postRepository.findAllByIdSmallerThanCursor(post.id!!, query.userId, pageable)
        } else {
            postRepository.findAllByIdBiggerThanCursor(post.id!!, query.userId, pageable)
        }

        return GetPrevPostResponseDto(
            unReadPostIds = prevPost.map { it.uuid }
        )
    }

    private fun idSortOrderToSort(sortOrder: SortOrder): Sort {
        return when (sortOrder) {
            SortOrder.ASCENDING -> Sort.by(Sort.Order.asc("id"))
            SortOrder.DESCENDING -> Sort.by(Sort.Order.desc("id"))
            else -> Sort.unsorted()
        }
    }

    private fun PollEntity.toDto(participantCount: Int): PollResponseDto {
        return PollResponseDto(
            id = this.id!!,
            title = this.title,
            participantCount = participantCount
        )
    }

    private fun PostEntity.toDto(participantCount: Int, imageUrl: String?): GetPostsResponseDtoPost {
        return GetPostsResponseDtoPost(
            id = this.uuid,
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
