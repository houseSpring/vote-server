package house.spring.vote.common.config

import house.spring.vote.common.domain.exception.CustomException
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component


@Aspect
@Component
@Profile("local")
class LoggingAspect {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Pointcut("within(house.spring.vote..*)")
    fun applicationPointCut() {
    }

    @Around("applicationPointCut()")
    fun logAround(joinPoint: ProceedingJoinPoint): Any? {
        val startTime = System.currentTimeMillis()
        val methodName = joinPoint.signature.name
        val className = joinPoint.signature.declaringTypeName
        val args = joinPoint.args.contentToString()

        logRequest(className, methodName, args)

        return try {
            val result = joinPoint.proceed()
            val endTime = System.currentTimeMillis()
            if (result is ResponseEntity<*>) {
                logResponse(className, methodName, result, endTime - startTime)
            } else {
                logger.info(
                    "RESPONSE: {}.{}() RESULT = {} DURATION = {} ms",
                    className,
                    methodName,
                    result,
                    endTime - startTime
                )
            }
            result
        } catch (e: CustomException) {
            val endTime = System.currentTimeMillis()
            logCustomException(className, methodName, args, e, endTime - startTime)
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            val endTime = System.currentTimeMillis()
            logException(className, methodName, args, e, endTime - startTime)
            throw e
        }
    }

    private fun logRequest(className: String, methodName: String, args: String) {
        logger.info("REQUEST: {}.{}() ARGS = {}", className, methodName, args)
    }

    private fun logResponse(className: String, methodName: String, result: ResponseEntity<*>, duration: Long) {
        val statusCode = result.statusCode
        val body = result.body
        if (body != null) {
            logger.info(
                "RESPONSE: {}.{}() STATUS = {} BODY = {} DURATION = {} ms",
                className,
                methodName,
                statusCode,
                body,
                duration
            )
        } else {
            logger.info(
                "RESPONSE: {}.{}() STATUS = {} BODY is null DURATION = {} ms",
                className,
                methodName,
                statusCode,
                duration
            )
        }
    }

    private fun logCustomException(
        className: String,
        methodName: String,
        args: String,
        e: CustomException,
        duration: Long,
    ) {
        logger.error(
            "CustomException: {} with errorCode: {} in {}.{}() with ARGS = {} DURATION = {} ms",
            e.message,
            e.status,
            className,
            methodName,
            args,
            duration
        )
    }

    private fun logException(className: String, methodName: String, args: String, e: Exception, duration: Long) {
        logger.error(
            "Exception: {} in {}.{}() with ARGS = {} DURATION = {} ms",
            e.message,
            className,
            methodName,
            args,
            duration
        )
    }
}