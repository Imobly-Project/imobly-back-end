package com.imobly.imobly.controllers.passwordrecovery

import com.imobly.imobly.controllers.passwordrecovery.dtos.EmailDTO
import com.imobly.imobly.controllers.passwordrecovery.dtos.ResetPasswordDTO
import com.imobly.imobly.services.PasswordRecoveryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/redefinirsenha")
class PasswordRecoveryController (
    val service: PasswordRecoveryService
){
    @PostMapping("/solicitarcodigo")
    fun requestCode(@RequestBody dto: EmailDTO): ResponseEntity<String> {
        service.getCode(dto.email)

        return ResponseEntity.status(HttpStatus.OK).build()

    }

    @PatchMapping("/criarnovasenha")
    fun resetPassword(@RequestBody dto: ResetPasswordDTO): ResponseEntity<Void> {
        service.changePassword(dto.email, dto.code, dto.newPassword)
        return ResponseEntity.status(HttpStatus.OK).build()
    }

}