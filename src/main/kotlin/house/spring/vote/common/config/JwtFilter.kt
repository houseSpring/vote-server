package house.spring.vote.common.config

import house.spring.vote.common.application.TokenProvider
import house.spring.vote.common.domain.CurrentUser
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter

class JwtFilter(
    private val tokenProvider: TokenProvider,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {

        if (!shouldFilter(request.requestURI, request.method)) {
            filterChain.doFilter(request, response)
            return
        }

        val authorizationHeader = request.getHeader("Authorization")
        val hasToken = authorizationHeader != null && authorizationHeader.startsWith("Bearer ")
        val token: String? = authorizationHeader?.substring(7).takeIf { hasToken }
        val currentUser: CurrentUser? = token?.let { tokenProvider.validateToken(it) }

        if (currentUser != null) {
            request.setAttribute("currentUser", currentUser)
            filterChain.doFilter(request, response)
            return
        }

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
    }

    private fun shouldFilter(requestURI: String, method: String): Boolean {
        return when {
            requestURI.startsWith("/posts") -> true
            requestURI == "/upload-url" -> true
            requestURI == "/users" && method == "GET" -> true
            requestURI.startsWith("/reports") -> true
            else -> false
        }
    }
}