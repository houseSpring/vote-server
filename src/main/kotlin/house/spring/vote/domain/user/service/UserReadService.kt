package house.spring.vote.domain.user.service

import house.spring.vote.domain.user.dto.response.GetUserInfoResponseDto


interface UserReadService {
    fun getUserInfo(userId: Long): GetUserInfoResponseDto
}