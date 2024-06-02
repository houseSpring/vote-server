package house.spring.vote.infrastructure.user.mapper

import house.spring.vote.application.user.port.UserMapper
import house.spring.vote.domain.user.model.User
import house.spring.vote.infrastructure.user.entity.UserEntity
import org.springframework.stereotype.Component

@Component
class UserMapperImpl : UserMapper {
    override fun toDomain(entity: UserEntity): User {
        return User(
            id = entity.id!!,
            nickname = entity.nickname,
            deviceId = entity.deviceId
        )
    }

    override fun toEntity(domain: User): UserEntity {
        return UserEntity(
            id = domain.id,
            nickname = domain.nickname,
            deviceId = domain.deviceId
        )
    }
}