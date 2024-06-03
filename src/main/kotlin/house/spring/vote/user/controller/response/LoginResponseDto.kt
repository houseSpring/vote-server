package house.spring.vote.user.controller.response

data class UserInfoDto(val id: Long, val nickname: String)

data class LoginResponseDto(val user: UserInfoDto, val token: String)