package house.spring.vote.interfaces.controller.error

import house.spring.vote.util.excaption.CustomException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(CustomException::class)
    fun handleCustomException(e: CustomException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(e.status.value(), e.message ?: e.status.reasonPhrase)
        return ResponseEntity(response, e.status)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errors = e.bindingResult.fieldErrors.associate { it.field to it.defaultMessage }
        println("MethodArgumentNotValidException")
        println(errors)
        val response = ErrorResponse(HttpStatus.BAD_REQUEST.value(), errors)
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleTypeMismatchException(e: MethodArgumentTypeMismatchException): ResponseEntity<ErrorResponse> {
        val error =
            "Invalid value '${e.value}' for parameter '${e.name}'. Expected type: ${e.requiredType?.simpleName}. Enum values: ${(e.requiredType?.enumConstants?.joinToString { it.toString() })}"
        println("MethodArgumentTypeMismatchException")
        println(error)
        val response = ErrorResponse(HttpStatus.BAD_REQUEST.value(), mapOf(e.name to error))
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
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