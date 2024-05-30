package house.spring.vote.application.user.service

import house.spring.vote.domain.user.repository.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        val user = userRepository.findById(username!!.toLong())
            .orElseThrow { UsernameNotFoundException("User not found with id: $username") }
        return User(user.id.toString(), "", emptyList())
    }
}