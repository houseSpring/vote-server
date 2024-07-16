package house.spring.vote.user.application.port.out

import house.spring.vote.user.domain.model.User
import house.spring.vote.user.infrastructure.entity.UserEntity

interface UserMapper {
    fun toDomain(entity: UserEntity): User
    fun toEntity(domain: User): UserEntity
}