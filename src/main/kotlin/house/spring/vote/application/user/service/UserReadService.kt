package house.spring.vote.application.user.service

import house.spring.vote.application.user.dto.query.GetUserInfoQuery
import house.spring.vote.interfaces.controller.user.response.GetUserInfoResponseDto


interface UserReadService {
    fun getUserInfo(query: GetUserInfoQuery): GetUserInfoResponseDto
}