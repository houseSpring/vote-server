package house.spring.vote.domain.model

import java.util.*

data class Post(
    val id: PostId = PostId(null, UUID.randomUUID().toString()),// TODO: UUID.randomUUID() 해결 필요
    val title: String,
    val userId: Long,
    val pickType: PickType,
    var imageKey: String? = null,
    val polls: List<Poll> = mutableListOf()
) {
    fun hasImage(): Boolean = imageKey != null
    fun hasPollId(pollId: Long): Boolean = polls.any { it.id == pollId }
    fun validatePickedPollsSize(size: Int): Boolean {
        return when (pickType) {
            PickType.Multi -> size >= 2
            PickType.Single -> size == 1
        }
    }

    fun validatePollsSize(): Boolean {
        return polls.size in (POLL_MIN_SIZE + 1)..<POLL_MAX_SIZE
    }

    companion object {
        const val POLL_MAX_SIZE = 10
        const val POLL_MIN_SIZE = 2
    }
}