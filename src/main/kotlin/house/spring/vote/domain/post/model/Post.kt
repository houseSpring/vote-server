package house.spring.vote.domain.post.model

import house.spring.vote.domain.post.repository.PickedPollRepository
import house.spring.vote.domain.validation.ValidationResult
import house.spring.vote.util.excaption.BadRequestException
import house.spring.vote.util.excaption.ConflictException
import house.spring.vote.util.excaption.ErrorCode
import house.spring.vote.util.excaption.NotFoundException

data class Post(
    val id: PostId = PostId(),
    val title: String,
    val userId: Long,
    val pickType: PickType,
    var imageKey: String? = null,
    val polls: List<Poll> = mutableListOf()
) {
    fun hasImage(): Boolean = imageKey != null

    fun validatePost(): ValidationResult {
        if (this.validatePollsSize()) {
            return ValidationResult.Error(BadRequestException(ErrorCode.INVALID_POLL_SIZE))
        }
        return ValidationResult.Success
    }

    fun validatePickedPoll(
        userId: Long,
        pickedPollIds: List<Long>,
        pickedPollRepository: PickedPollRepository
    ): ValidationResult {
        if (hasUserAlreadyPicked(this.id.incrementId!!, userId, pickedPollRepository)) {
            return ValidationResult.Error(ConflictException("${ErrorCode.ALREADY_PICKED_POST} (${this.id.uuid})"))
        }
        if (hasInvalidPollId(pickedPollIds)) {
            return ValidationResult.Error(NotFoundException("${ErrorCode.POLL_NOT_FOUND} (${pickedPollIds.joinToString()})"))
        }
        if (!validatePickedPollsSize(pickedPollIds.size)) {
            return ValidationResult.Error(BadRequestException(ErrorCode.INVALID_PICKED_POLL_SIZE))
        }
        return ValidationResult.Success
    }

    private fun validatePollsSize(): Boolean {
        return polls.size in (POLL_MIN_SIZE + 1)..<POLL_MAX_SIZE
    }

    private fun validatePickedPollsSize(size: Int): Boolean {
        return when (pickType) {
            PickType.Multi -> size >= 2
            PickType.Single -> size == 1
        }
    }

    private fun hasUserAlreadyPicked(postId: Long, userId: Long, pickedPollRepository: PickedPollRepository): Boolean {
        return pickedPollRepository.findAllByPostIdAndUserId(postId, userId).isNotEmpty()
    }

    private fun hasInvalidPollId(pollIds: List<Long>): Boolean {
        return pollIds.any { !this.hasPollId(it) }
    }

    private fun hasPollId(pollId: Long): Boolean = polls.any { it.id == pollId }

    companion object {
        const val POLL_MAX_SIZE = 10
        const val POLL_MIN_SIZE = 2
    }
}