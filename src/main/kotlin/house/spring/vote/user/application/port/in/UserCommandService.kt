package house.spring.vote.user.application.port.`in`

import house.spring.vote.user.application.command.DeviceJoinCommand
import house.spring.vote.user.application.command.DeviceLoginCommand

interface UserCommandService {
    fun createDeviceUser(command: DeviceJoinCommand): String
    fun loginDeviceUser(command: DeviceLoginCommand): String
}