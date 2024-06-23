package house.spring.vote.post.domain.model

import house.spring.vote.common.domain.exception.ErrorCode
import java.util.*

data class Poll(
    val id: String,
    val title: String,
) {
    init {
        require(UUID.fromString(id) != null) { ErrorCode.INVALID_ARGUMENT + " (id: $id)" }
        require(title.isNotBlank()) { "Title must not be blank" }
    }

    companion object {
        fun create(title: String): Poll {
            return Poll(id = UUID.randomUUID().toString(), title = title)
        }
    }
}
