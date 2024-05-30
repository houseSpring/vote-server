package house.spring.vote.domain.user.factory

import org.springframework.security.core.userdetails.UserDetails

interface UserDetailsFactory {
    fun create(username: String): UserDetails
}