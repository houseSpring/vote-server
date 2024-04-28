package house.spring.vote.domain.user.dto

data class UserInfoDto(val id: Long, val nickname: String)

data class LoginResponseDto(val user: UserInfoDto, val token: String)