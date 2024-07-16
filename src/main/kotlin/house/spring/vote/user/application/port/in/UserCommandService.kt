package house.spring.vote.user.application.port.`in`

import house.spring.vote.user.application.command.DeviceJoinCommand

interface UserCommandService {
    fun createDeviceUser(command: DeviceJoinCommand): String
}