package house.spring.vote.interfaces.controller.user.response

data class UserInfoDto(val id: Long, val nickname: String)

data class LoginResponseDto(val user: UserInfoDto, val token: String)