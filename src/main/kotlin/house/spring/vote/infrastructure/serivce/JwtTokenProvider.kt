package house.spring.vote.infrastructure.serivce

import house.spring.vote.domain.post.service.TokenProvider
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtTokenProvider(
    @Value("\${jwt.expiration}") private val expiration: Long,
    @Value("\${jwt.secret}") private val secretKey: String,
    @Value("\${jwt.issuer}") private val issuer: String,
) : TokenProvider {

    private val key: SecretKey = Keys.hmacShaKeyFor(secretKey.toByteArray())
    override fun extractUsername(token: String): String {
        return extractClaim(token, Claims::getSubject)
    }

    override fun generateToken(userDetails: UserDetails): String {
        val claims: Map<String, Any> = HashMap()
        return createToken(claims, userDetails.username)
    }

    override fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return (username == userDetails.username && !isTokenExpired(token))
    }

    private fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver.invoke(claims)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
    }

    private fun createToken(claims: Map<String, Any>, subject: String): String {
        val now = Date()
        return Jwts.builder()
            .signWith(key)
            .issuer(issuer)
            .subject(subject)
            .claims(claims)
            .issuedAt(Date())
            .encodePayload(true)
            .expiration(Date(now.time + expiration))
            .compact()
    }

    private fun isTokenExpired(token: String): Boolean {
        val expiration = extractClaim(token, Claims::getExpiration)
        return expiration.before(Date())
    }
}