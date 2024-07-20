package house.spring.vote.post.domain.model

import house.spring.vote.common.domain.exception.ErrorCode
import house.spring.vote.common.domain.exception.bad_request.InvalidPickedPollSizeException
import house.spring.vote.common.domain.exception.not_found.PickedPollNotFoundException
import house.spring.vote.post.domain.event.PickedPollEvent
import java.util.*

data class Post(
    val id: String,
    val title: String,
    val userId: String,
    val pickType: PickType,
    val imageKey: String? = null,
    val polls: List<Poll>,
) {

    init {
        require(UUID.fromString(id) != null) { ErrorCode.INVALID_ARGUMENT + " (id: $id)" }
        require(title.isNotBlank()) { ErrorCode.INVALID_ARGUMENT + " (title: $title)" }
        require(polls.size in POLL_MIN_SIZE..POLL_MAX_SIZE) { ErrorCode.INVALID_POLL_SIZE + " (polls: ${polls.size})" }
    }

    val events = mutableListOf<Any>()

    fun hasImage(): Boolean = imageKey != null

    fun createPickedPolls(pickedPollIds: List<String>): List<PickedPoll> {
        validatePickedPoll(pickedPollIds)
        return pickedPollIds.map { PickedPoll.create(id, it, userId) }
    }


    fun addImageKey(imageKey: String): Post {
        return this.copy(imageKey = imageKey)
    }

    fun addEvents(events: List<Any>) {
        this.events.addAll(events)
    }

    private fun validatePickedPoll(
        pickedPollIds: List<String>,
    ) {
        if (isPickedPollsSizeValid(pickedPollIds.size)) {
            throw InvalidPickedPollSizeException("(pickedPollIds: ${pickedPollIds.joinToString()}")
        }

        if (hasInvalidPollId(pickedPollIds)) {
            throw PickedPollNotFoundException("(pollIds: ${pickedPollIds.joinToString()})")
        }
    }

    private fun isPickedPollsSizeValid(size: Int): Boolean {
        return when (pickType) {
            PickType.Multi -> size >= 2
            PickType.Single -> size == 1
        }
    }

    private fun hasInvalidPollId(pollIds: List<String>): Boolean {
        return pollIds.any { !this.hasPollId(it) }
    }

    private fun hasPollId(pollId: String): Boolean = polls.any { it.id == pollId }

    companion object {
        const val POLL_MAX_SIZE = 10
        const val POLL_MIN_SIZE = 2

        fun create(
            id: String = UUID.randomUUID().toString(),
            title: String,
            userId: String,
            pickType: PickType,
            imageKey: String? = null,
            polls: List<Poll>,
        ): Post {
            return Post(id, title, userId, pickType, imageKey, polls)
        }
    }
}