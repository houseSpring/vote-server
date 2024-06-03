package house.spring.vote.user.infrastructure.mapper

import house.spring.vote.user.application.port.UserMapper
import house.spring.vote.user.domain.model.User
import house.spring.vote.user.infrastructure.entity.UserEntity
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