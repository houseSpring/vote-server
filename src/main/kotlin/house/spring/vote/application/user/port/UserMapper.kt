package house.spring.vote.application.user.port

import house.spring.vote.domain.user.model.User
import house.spring.vote.infrastructure.user.entity.UserEntity

interface UserMapper {
    fun toDomain(entity: UserEntity): User
    fun toEntity(domain: User): UserEntity
}