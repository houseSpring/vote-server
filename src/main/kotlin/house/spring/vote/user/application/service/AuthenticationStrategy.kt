package house.spring.vote.user.application.service

import house.spring.vote.user.application.command.AuthenticationCommand
import house.spring.vote.user.application.repository.UserRepository
import house.spring.vote.common.domain.exception.ErrorCode
import house.spring.vote.common.domain.exception.UnAuthorizedException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

interface AuthenticationStrategy {
    fun authenticate(command: AuthenticationCommand): UserDetails
}

@Service("deviceIdAuthenticationStrategy")
class DeviceIdAuthenticationStrategy(
    private val userRepository: UserRepository,
    private val customUserDetailsService: CustomUserDetailsService
) : AuthenticationStrategy {
    override fun authenticate(command: AuthenticationCommand): UserDetails {
        val user = userRepository.findByDeviceId(command.deviceId)
            ?: throw UnAuthorizedException("${ErrorCode.USER_NOT_FOUND} (deviceId: ${command.deviceId})")
        return customUserDetailsService.createUserDetails(user)
    }
}

// TODO: 이메일 로그인 이후 디테일 구현 필요
@Service("emailPasswordAuthenticationStrategy")
class EmailPasswordAuthenticationStrategy(
    private val userRepository: UserRepository,
    private val customUserDetailsService: CustomUserDetailsService
) : AuthenticationStrategy {
    override fun authenticate(command: AuthenticationCommand): UserDetails {
        val user = userRepository.findByDeviceId(command.deviceId)
            ?: throw UnAuthorizedException("${ErrorCode.USER_NOT_FOUND} (deviceId: ${command.deviceId})")
        return customUserDetailsService.createUserDetails(user)
    }
}