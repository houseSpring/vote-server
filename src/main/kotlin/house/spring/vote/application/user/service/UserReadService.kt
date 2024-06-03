package house.spring.vote.application.user.service

import house.spring.vote.application.user.repository.UserRepository
import house.spring.vote.interfaces.controller.user.response.GetUserInfoResponseDto
import house.spring.vote.util.excaption.ErrorCode
import house.spring.vote.util.excaption.NotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class UserReadService(private val userRepository: UserRepository) {
    fun getUserInfoById(userId: Long): GetUserInfoResponseDto {
        val user = userRepository.findById(userId)
            ?: throw NotFoundException("${ErrorCode.USER_NOT_FOUND} (${userId})")
        return GetUserInfoResponseDto(user.id!!, user.nickname)
    }
}