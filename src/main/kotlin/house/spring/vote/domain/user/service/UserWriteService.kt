package house.spring.vote.domain.user.service

interface UserWriteService {
    fun join(nickname: String, deviceId: String): Long
    fun login(deviceId: String): String
}