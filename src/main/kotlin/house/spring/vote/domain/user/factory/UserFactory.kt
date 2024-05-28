package house.spring.vote.domain.user.factory

import house.spring.vote.domain.user.model.User
import org.springframework.stereotype.Component

@Component
class UserFactory {
    fun create(nickname: String, deviceId: String): User {
        return User(nickname = nickname, deviceId = deviceId)
    }

    fun reconstitute(id: Long, nickname: String, deviceId: String): User {
        return User(id = id, nickname = nickname, deviceId = deviceId)
    }

}