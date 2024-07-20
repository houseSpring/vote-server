package house.spring.vote.common.infrastructure

import house.spring.vote.common.application.TokenProvider
import house.spring.vote.common.domain.CurrentUser
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
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


    override fun generateToken(currnetUser: CurrentUser): String {
        val claims: Map<String, Any> = HashMap()
        return createToken(claims, currnetUser.id)
    }

    override fun validateToken(token: String): CurrentUser {
        val payload = extractAllClaims(token)
        return CurrentUser(
            id = payload.subject,
            deviceId = payload["deviceId"].toString(),
        )
    }

//    private fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
//        val claims = extractAllClaims(token)
//        return claimsResolver.invoke(claims)
//    }

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

}