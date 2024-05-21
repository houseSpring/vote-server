package house.spring.vote.interfaces.controller.error

import house.spring.vote.application.error.CustomException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(CustomException::class)
    fun handleCustomException(e: CustomException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(e.status.value(), e.message ?: e.status.reasonPhrase)
        return ResponseEntity(response, e.status)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        val internalServerError = HttpStatus.INTERNAL_SERVER_ERROR
        val response = ErrorResponse(
            internalServerError.value(),
            e.message ?: internalServerError.reasonPhrase
        )
        return ResponseEntity(response, internalServerError)
    }
}