package house.spring.vote.user.domain.model

import house.spring.vote.common.domain.exception.ErrorCode
import java.util.*

class User(
    val id: String,
    var deviceId: String? = null,
    val nickname: String,
) {
    init {
        require(UUID.fromString(id) != null) { ErrorCode.INVALID_ARGUMENT + " (id: $id)" }
        require(nickname.isNotBlank()) { "Nickname must not be blank" }
    }

    companion object {
        fun create(deviceId: String, nickname: String): User {
            return User(id = UUID.randomUUID().toString(), deviceId = deviceId, nickname = nickname)
        }
    }
}