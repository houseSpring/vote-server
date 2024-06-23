package house.spring.vote.user.application.service

import house.spring.vote.user.application.repository.UserRepository
import house.spring.vote.user.controller.response.GetUserInfoResponseDto
import house.spring.vote.common.domain.exception.ErrorCode
import house.spring.vote.common.domain.exception.not_found.NotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class UserReadService(private val userRepository: UserRepository) {
    fun getUserInfoById(userId: String): GetUserInfoResponseDto {
        val user = userRepository.findById(userId)
            ?: throw NotFoundException("${ErrorCode.USER_NOT_FOUND} (${userId})")
        return GetUserInfoResponseDto(user.id, user.nickname)
    }
}