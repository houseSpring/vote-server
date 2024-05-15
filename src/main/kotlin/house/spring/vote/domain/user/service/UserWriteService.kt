package house.spring.vote.domain.user.service

import house.spring.vote.domain.user.dto.command.JoinCommand
import house.spring.vote.domain.user.dto.command.LoginCommand

interface UserWriteService {
    fun join(command: JoinCommand): Long
    fun login(command: LoginCommand): String
}