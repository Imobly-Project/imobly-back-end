package com.imobly.imobly.controllers.exceptionhandler

import com.imobly.imobly.controllers.exceptionhandler.dtos.ErrorFieldDTO
import com.imobly.imobly.controllers.exceptionhandler.dtos.ErrorMessageDTO
import com.imobly.imobly.exceptions.ResourceNotFoundException
import com.imobly.imobly.exceptions.enums.RuntimeErrorEnum
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.Instant

@ControllerAdvice
class ExceptionHandlerController {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValid(
        exception: MethodArgumentNotValidException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorMessageDTO> {

        val errors: List<ErrorFieldDTO> = exception.bindingResult.fieldErrors.map {
            ErrorFieldDTO(
                name = it.field,
                description = it.defaultMessage ?: "",
                value = it.rejectedValue.toString()
            )
        }

        val enum: RuntimeErrorEnum = RuntimeErrorEnum.ERR0001
        val status: HttpStatus = HttpStatus.BAD_REQUEST
        val error = ErrorMessageDTO(
            code = enum.code,
            status = status.value(),
            message = enum.message,
            timestamp = Instant.now(),
            path = request.requestURI,
            errorFields = errors
        )
        return ResponseEntity.status(status).body(error)
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun resourceNotFound(
        exception: ResourceNotFoundException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorMessageDTO>
    {
        val enum: RuntimeErrorEnum = exception.errorEnum
        val status: HttpStatus = HttpStatus.NOT_FOUND
        val error = ErrorMessageDTO(
            code = enum.code,
            status = status.value(),
            message = enum.message,
            timestamp = Instant.now(),
            path = request.requestURI,
        )
        return ResponseEntity.status(status).body(error)
    }
}