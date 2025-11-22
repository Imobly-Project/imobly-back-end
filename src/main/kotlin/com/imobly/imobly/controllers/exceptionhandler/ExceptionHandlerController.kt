package com.imobly.imobly.controllers.exceptionhandler

import com.imobly.imobly.controllers.exceptionhandler.dtos.ErrorFieldDTO
import com.imobly.imobly.controllers.exceptionhandler.dtos.ErrorMessageDTO
import com.imobly.imobly.exceptions.AuthenticationFailedException
import com.imobly.imobly.exceptions.DuplicateResourceException
import com.imobly.imobly.exceptions.InternalErrorException
import com.imobly.imobly.exceptions.InvalidArgumentsException
import com.imobly.imobly.exceptions.OperationNotAllowedException
import com.imobly.imobly.exceptions.ResourceNotFoundException
import com.imobly.imobly.exceptions.UnsupportedMediaTypeException
import com.imobly.imobly.exceptions.enums.RuntimeErrorEnum
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.Instant

@ControllerAdvice
class ExceptionHandlerController {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValid(
        exception: MethodArgumentNotValidException, request: HttpServletRequest
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
        exception: ResourceNotFoundException, request: HttpServletRequest
    ): ResponseEntity<ErrorMessageDTO> {
        val enum: RuntimeErrorEnum = exception.errorEnum
        val status: HttpStatus = HttpStatus.NOT_FOUND
        val error = ErrorMessageDTO(
            code = enum.code,
            status = status.value(),
            message = enum.message,
            timestamp = Instant.now(),
            path = request.requestURI
        )
        return ResponseEntity.status(status).body(error)
    }

    @ExceptionHandler(OperationNotAllowedException::class)
    fun operationNotAllowed(
        exception: OperationNotAllowedException, request: HttpServletRequest
    ): ResponseEntity<ErrorMessageDTO> {
        val enum: RuntimeErrorEnum = exception.errorEnum
        val status: HttpStatus = HttpStatus.CONFLICT
        val error = ErrorMessageDTO(
            code = enum.code,
            status = status.value(),
            message = enum.message,
            timestamp = Instant.now(),
            path = request.requestURI
        )
        return ResponseEntity.status(status).body(error)
    }

    @ExceptionHandler(DuplicateResourceException::class)
    fun duplicateResource(
        exception: DuplicateResourceException, request: HttpServletRequest
    ): ResponseEntity<ErrorMessageDTO> {
        val enum: RuntimeErrorEnum = exception.errorEnum
        val status: HttpStatus = HttpStatus.CONFLICT
        val error = ErrorMessageDTO(
            code = enum.code,
            status = status.value(),
            message = enum.message,
            timestamp = Instant.now(),
            path = request.requestURI
        )
        return ResponseEntity.status(status).body(error)
    }

    @ExceptionHandler(BadCredentialsException::class)
    fun badCredentials(request: HttpServletRequest): ResponseEntity<ErrorMessageDTO> {
        val enum: RuntimeErrorEnum = RuntimeErrorEnum.ERR0019
        val status: HttpStatus = HttpStatus.FORBIDDEN
        val error = ErrorMessageDTO(
            code = enum.code,
            status = status.value(),
            message = enum.message,
            timestamp = Instant.now(),
            path = request.requestURI
        )
        return ResponseEntity.status(status).body(error)
    }

    @ExceptionHandler(InternalErrorException::class)
    fun internalError(
        exception: InternalErrorException, request: HttpServletRequest
    ): ResponseEntity<ErrorMessageDTO> {
        val enum: RuntimeErrorEnum = exception.errorEnum
        val status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR
        val error = ErrorMessageDTO(
            code = enum.code,
            status = status.value(),
            message = enum.message,
            timestamp = Instant.now(),
            path = request.requestURI
        )
        return ResponseEntity.status(status).body(error)
    }

    @ExceptionHandler(UnsupportedMediaTypeException::class)
    fun unsupportedMediaType(
        exception: UnsupportedMediaTypeException, request: HttpServletRequest
    ): ResponseEntity<ErrorMessageDTO> {
        val enum: RuntimeErrorEnum = exception.errorEnum
        val status: HttpStatus = HttpStatus.UNSUPPORTED_MEDIA_TYPE
        val error = ErrorMessageDTO(
            code = enum.code,
            status = status.value(),
            message = enum.message,
            timestamp = Instant.now(),
            path = request.requestURI
        )
        return ResponseEntity.status(status).body(error)
    }

    @ExceptionHandler(InvalidArgumentsException::class)
    fun invalidArguments(
        exception: InvalidArgumentsException, request: HttpServletRequest
    ): ResponseEntity<ErrorMessageDTO> {
        val enum: RuntimeErrorEnum = exception.errorEnum
        val status: HttpStatus = HttpStatus.BAD_REQUEST
        val error = ErrorMessageDTO(
            code = enum.code,
            status = status.value(),
            message = enum.message,
            timestamp = Instant.now(),
            path = request.requestURI
        )
        return ResponseEntity.status(status).body(error)
    }

    @ExceptionHandler(AuthenticationFailedException::class)
    fun authenticationFailed(
        exception: AuthenticationFailedException, request: HttpServletRequest
    ): ResponseEntity<ErrorMessageDTO> {
        val enum: RuntimeErrorEnum = exception.errorEnum
        val status: HttpStatus = HttpStatus.FORBIDDEN
        val error = ErrorMessageDTO(
            code = enum.code,
            status = status.value(),
            message = enum.message,
            timestamp = Instant.now(),
            path = request.requestURI
        )
        return ResponseEntity.status(status).body(error)
    }
}