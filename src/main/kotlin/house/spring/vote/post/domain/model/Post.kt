package house.spring.vote.post.domain.model

import house.spring.vote.common.domain.validation.ValidationResult
import house.spring.vote.common.domain.exception.BadRequestException
import house.spring.vote.common.domain.exception.ErrorCode
import house.spring.vote.common.domain.exception.NotFoundException
import java.time.LocalDateTime

// TODO: VO 검증 로직 추가
data class Post(
    val id: PostId = PostId(),
    val title: String,
    val userId: Long,
    val pickType: PickType,
    var imageKey: String? = null,
    val polls: List<Poll> = mutableListOf(),
    val createdAt: LocalDateTime = LocalDateTime.now(),
) {

    var updatedAt: LocalDateTime = LocalDateTime.now()
        private set

    constructor(
        id: PostId,
        title: String,
        userId: Long,
        pickType: PickType,
        imageKey: String?,
        polls: List<Poll>,
        updatedAt: LocalDateTime,
        createdAt: LocalDateTime
    ) : this(id, title, userId, pickType, imageKey, polls, createdAt) {
        this.updatedAt = updatedAt
    }

    fun hasImage(): Boolean = imageKey != null

    fun validateForCreation(): ValidationResult {
        return if (!this.isPollsSizeValid()) {
            ValidationResult.Error(BadRequestException(ErrorCode.INVALID_POLL_SIZE))
        } else {
            ValidationResult.Success
        }
    }

    fun validatePickedPoll(
        pickedPollIds: List<Long>,
    ): ValidationResult {
        return if (!isPickedPollsSizeValid(pickedPollIds.size)) {
            ValidationResult.Error(BadRequestException(ErrorCode.INVALID_PICKED_POLL_SIZE))
        } else if (hasInvalidPollId(pickedPollIds)) {
            ValidationResult.Error(NotFoundException("${ErrorCode.POLL_NOT_FOUND} (${pickedPollIds.joinToString()})"))
        } else {
            ValidationResult.Success
        }
    }

    private fun isPollsSizeValid(): Boolean {
        return polls.size in (POLL_MIN_SIZE + 1)..<POLL_MAX_SIZE
    }

    private fun isPickedPollsSizeValid(size: Int): Boolean {
        return when (pickType) {
            PickType.Multi -> size >= 2
            PickType.Single -> size == 1
        }
    }

    private fun hasInvalidPollId(pollIds: List<Long>): Boolean {
        return pollIds.any { !this.hasPollId(it) }
    }

    private fun hasPollId(pollId: Long): Boolean = polls.any { it.id == pollId }

    companion object {
        const val POLL_MAX_SIZE = 10
        const val POLL_MIN_SIZE = 2

        fun create(
            id: PostId = PostId(),
            title: String,
            userId: Long,
            pickType: PickType,
            imageKey: String? = null,
            polls: List<Poll> = mutableListOf(),
        ): Post {
            return Post(id, title, userId, pickType, imageKey, polls)
        }
    }
}