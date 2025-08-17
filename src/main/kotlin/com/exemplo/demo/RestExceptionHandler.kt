package com.exemplo.demo

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.Instant

@RestControllerAdvice
class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(ex: MethodArgumentNotValidException): ResponseEntity<Any> {
        val errors = ex.bindingResult.allErrors.map { err ->
            val field = (err as? FieldError)?.field
            mapOf(
                "field" to field,
                "message" to (err.defaultMessage ?: "invalid")
            )
        }
        val body = mapOf(
            "timestamp" to Instant.now().toString(),
            "status" to 400,
            "errors" to errors
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body)
    }
}
