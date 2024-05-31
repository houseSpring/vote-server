import house.spring.vote.application.user.dto.command.AuthenticationCommand
import house.spring.vote.application.user.port.UserMapper
import house.spring.vote.application.user.service.CustomUserDetailsService
import house.spring.vote.domain.user.repository.UserRepository
import house.spring.vote.util.excaption.ErrorCode
import house.spring.vote.util.excaption.NotFoundException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

interface AuthenticationStrategy {
    fun authenticate(command: AuthenticationCommand): UserDetails
}

@Service("deviceIdAuthenticationStrategy")
class DeviceIdAuthenticationStrategy(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
    private val customUserDetailsService: CustomUserDetailsService
) : AuthenticationStrategy {
    override fun authenticate(command: AuthenticationCommand): UserDetails {
        val user = userRepository.findByDeviceId(command.deviceId)
            .orElseThrow {
                NotFoundException("${ErrorCode.USER_NOT_FOUND} (deviceId: ${command.deviceId})")
            }
        return customUserDetailsService.createUserDetails(userMapper.toDomain(user))
    }
}

@Service("emailPasswordAuthenticationStrategy")
class EmailPasswordAuthenticationStrategy(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
    private val customUserDetailsService: CustomUserDetailsService
) : AuthenticationStrategy {
    override fun authenticate(command: AuthenticationCommand): UserDetails {
        val user = userRepository.findByDeviceId(command.deviceId)
            .orElseThrow {
                NotFoundException("${ErrorCode.USER_NOT_FOUND} (deviceId: ${command.deviceId})")
            }
        return customUserDetailsService.createUserDetails(userMapper.toDomain(user))
    }
}