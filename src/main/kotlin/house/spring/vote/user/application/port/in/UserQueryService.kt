package house.spring.vote.user.application.port.`in`

import house.spring.vote.user.controller.response.GetUserInfoResponseDto

interface UserQueryService {
    fun getUserInfoById(userId: String): GetUserInfoResponseDto
}