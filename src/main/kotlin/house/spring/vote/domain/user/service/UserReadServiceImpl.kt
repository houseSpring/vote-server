package house.spring.vote.domain.user.service

import house.spring.vote.domain.user.dto.GetUserInfoResponseDto
import house.spring.vote.domain.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class UserReadServiceImpl(private val userRepository: UserRepository) : UserReadService {
    override fun getUserInfo(userId: Long): GetUserInfoResponseDto {
        val user = userRepository.findById(userId).orElseThrow { IllegalArgumentException("User not found") }
        return GetUserInfoResponseDto(user.id!!, user.nickname)
    }
}