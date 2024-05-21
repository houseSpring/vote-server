package house.spring.vote.application.user.service

import house.spring.vote.application.error.NotFoundException
import house.spring.vote.application.user.dto.query.GetUserInfoQuery
import house.spring.vote.domain.repository.UserRepository
import house.spring.vote.interfaces.controller.user.response.GetUserInfoResponseDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class UserReadServiceImpl(private val userRepository: UserRepository) : UserReadService {
    override fun getUserInfo(query: GetUserInfoQuery): GetUserInfoResponseDto {
        val user = userRepository.findById(query.userId)
            .orElseThrow { NotFoundException("사용자를 찾을 수 없습니다. (${query.userId})") }
        return GetUserInfoResponseDto(user.id!!, user.nickname)
    }
}