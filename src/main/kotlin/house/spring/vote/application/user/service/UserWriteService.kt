package house.spring.vote.application.user.service

import house.spring.vote.application.user.dto.command.JoinCommand
import house.spring.vote.application.user.dto.command.LoginCommand

interface UserWriteService {
    fun join(command: JoinCommand): Long
    fun login(command: LoginCommand): String
}