package house.spring.vote.user.application.service

import house.spring.vote.common.domain.exception.ErrorCode
import house.spring.vote.common.domain.exception.UnAuthorizedException
import house.spring.vote.user.application.command.AuthenticationCommand
import house.spring.vote.user.application.service.strategy.AuthenticationStrategy
import house.spring.vote.user.application.service.strategy.AuthenticationStrategyType
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val strategies: Map<String, AuthenticationStrategy>,
) {
    fun authenticate(command: AuthenticationCommand): UserDetails {
        val strategyKey = determineStrategyKey(command)
        val strategy = strategies[strategyKey] ?: throw UnAuthorizedException(ErrorCode.UNAUTHORIZED)
        return strategy.authenticate(command)
    }

    private fun determineStrategyKey(request: AuthenticationCommand): String {
        return when {
            request.deviceId.isNotBlank() -> AuthenticationStrategyType.DEVICE_ID.key
            else -> throw UnAuthorizedException(ErrorCode.UNAUTHORIZED)
        }
    }
}