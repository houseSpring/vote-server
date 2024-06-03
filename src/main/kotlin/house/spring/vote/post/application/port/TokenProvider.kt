package house.spring.vote.post.application.port

import org.springframework.security.core.userdetails.UserDetails

interface TokenProvider {
    fun extractUsername(token: String): String
    fun generateToken(userDetails: UserDetails): String
    fun validateToken(token: String, userDetails: UserDetails): Boolean
}