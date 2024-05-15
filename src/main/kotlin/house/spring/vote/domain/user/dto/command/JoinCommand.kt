package house.spring.vote.domain.user.dto.command

data class JoinCommand(
    val nickname: String, val deviceId: String
)
