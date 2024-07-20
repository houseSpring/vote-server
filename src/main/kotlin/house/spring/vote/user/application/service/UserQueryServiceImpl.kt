package house.spring.vote.user.application.service

import house.spring.vote.common.domain.exception.ErrorCode
import house.spring.vote.common.domain.exception.not_found.NotFoundException
import house.spring.vote.user.application.port.`in`.UserQueryService
import house.spring.vote.user.application.repository.UserRepository
import house.spring.vote.user.controller.response.GetUserInfoResponseDto
import org.springframework.stereotype.Service

@Service
class UserQueryServiceImpl(private val userRepository: UserRepository) : UserQueryService {
    override fun getUserInfoById(userId: String): GetUserInfoResponseDto {
        val user = userRepository.findById(userId)
            ?: throw NotFoundException("${ErrorCode.USER_NOT_FOUND} (userId: ${userId})")
        return GetUserInfoResponseDto(user.id, user.nickname)
    }
}