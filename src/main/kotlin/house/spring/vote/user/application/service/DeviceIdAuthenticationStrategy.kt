package house.spring.vote.user.application.service

import house.spring.vote.common.domain.exception.ErrorCode
import house.spring.vote.common.domain.exception.UnAuthorizedException
import house.spring.vote.user.application.command.AuthenticationCommand
import house.spring.vote.user.application.repository.UserRepository
import house.spring.vote.user.application.service.strategy.AuthenticationStrategy
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service


@Service("deviceIdAuthenticationStrategy")
class DeviceIdAuthenticationStrategy(
    private val userRepository: UserRepository,
    private val customUserDetailsService: CustomUserDetailsService,
) : AuthenticationStrategy {
    override fun authenticate(command: AuthenticationCommand): UserDetails {
        val user = userRepository.findByDeviceId(command.deviceId)
            ?: throw UnAuthorizedException("${ErrorCode.USER_NOT_FOUND} (deviceId: ${command.deviceId})")
        return customUserDetailsService.createUserDetails(user)
    }
}