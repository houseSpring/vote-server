package house.spring.vote.util

import org.springframework.stereotype.Component
import java.security.MessageDigest

@Component
class HashUtil {
    fun hash(input: String): String {
        val bytes = input.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }
}