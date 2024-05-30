package house.spring.vote.domain.post.service

import org.springframework.security.core.userdetails.UserDetails

interface TokenProvider {
    fun extractUsername(token: String): String
    fun generateToken(userDetails: UserDetails): String
    fun validateToken(token: String, userDetails: UserDetails): Boolean
}