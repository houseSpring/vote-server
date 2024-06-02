package house.spring.vote.application.user.service

import house.spring.vote.domain.user.repository.UserRepository
import house.spring.vote.domain.user.model.Role
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import house.spring.vote.domain.user.model.User as UserDomain

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    fun createUserDetails(user: UserDomain): UserDetails {
        return User(user.id.toString(), "", generateDefaultAuthorities())
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        val user = userRepository.findById(username!!.toLong())
            .orElseThrow { UsernameNotFoundException("User not found with id: $username") }
        return User(user.id.toString(), "", generateDefaultAuthorities())
    }

    private fun generateDefaultAuthorities(): List<SimpleGrantedAuthority> {
        return listOf(SimpleGrantedAuthority(Role.USER.name))
    }
}