package house.spring.vote.user.controller.response

data class LoginResponseDto(val user: UserInfoDto, val token: String) {
    data class UserInfoDto(val id: Long, val nickname: String)
}