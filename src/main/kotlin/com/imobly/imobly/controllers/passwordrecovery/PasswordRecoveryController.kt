package com.imobly.imobly.controllers.passwordrecovery

import com.imobly.imobly.controllers.passwordrecovery.dtos.EmailDTO
import com.imobly.imobly.controllers.passwordrecovery.dtos.ResetPasswordDTO
import com.imobly.imobly.exceptions.OperationNotAllowedException
import com.imobly.imobly.exceptions.enums.RuntimeErrorEnum
import com.imobly.imobly.services.PasswordRecoveryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/redefinirsenha")
class PasswordRecoveryController (
    private val service: PasswordRecoveryService
) {
    @PostMapping("/solicitarcodigo")
    fun requestCode(@RequestBody dto: EmailDTO): ResponseEntity<String> {
        service.getCode(dto.email)
        return ResponseEntity.status(HttpStatus.OK).build()
    }

    @GetMapping("/validartoken/{email}/{code}")
    fun validateCode(@PathVariable email: String, @PathVariable code: String): ResponseEntity<Void> {
        if ( !service.validateToken(email, code) ) {
            throw OperationNotAllowedException(RuntimeErrorEnum.ERR0021)
        }
        return ResponseEntity.status(HttpStatus.OK).build()
    }

    @PatchMapping("/criarnovasenha")
    fun resetPassword(@RequestBody dto: ResetPasswordDTO): ResponseEntity<Void> {
        service.changePassword(dto.email, dto.code, dto.newPassword)
        return ResponseEntity.status(HttpStatus.OK).build()
    }
}