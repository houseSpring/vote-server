package house.spring.vote.application.user.dto.command

data class JoinCommand(
    val nickname: String, val deviceId: String
)
