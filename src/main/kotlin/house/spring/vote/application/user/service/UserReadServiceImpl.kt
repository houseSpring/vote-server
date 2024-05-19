package house.spring.vote.application.user.service

import house.spring.vote.application.user.dto.query.GetUserInfoQuery
import house.spring.vote.domain.post.interfaces.controller.user.response.GetUserInfoResponseDto
import house.spring.vote.domain.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class UserReadServiceImpl(private val userRepository: UserRepository) : UserReadService {
    override fun getUserInfo(qeury: GetUserInfoQuery): GetUserInfoResponseDto {
        val user = userRepository.findById(qeury.userId).orElseThrow { IllegalArgumentException("User not found") }
        return GetUserInfoResponseDto(user.id!!, user.nickname)
    }
}