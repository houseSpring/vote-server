package house.spring.vote.user.application.service.strategy

import house.spring.vote.user.application.command.AuthenticationCommand
import org.springframework.security.core.userdetails.UserDetails

interface AuthenticationStrategy {
    fun authenticate(command: AuthenticationCommand): UserDetails
}