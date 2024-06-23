package house.spring.vote.common.infrastructure

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class LoggingInterceptor : HandlerInterceptor {

    private final val logger = LoggerFactory.getLogger(this.javaClass)

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        logger.info("Request URL: (${request.method})${request.requestURI} FROM: ${request.remoteAddr}")
        request.setAttribute("startTime", System.currentTimeMillis())
        return true
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?,
    ) {
        val startTime = request.getAttribute("startTime") as Long
        val endTime = System.currentTimeMillis()
        val executionTime = endTime - startTime

        logger.info("Response URL: e(${request.method})${request.requestURI} (${response.status})- TIME: $executionTime ms")
        if (ex != null) {
            logger.error("Exception: ${ex.message}")
            logger.error(ex.stackTraceToString())
        }

    }

}