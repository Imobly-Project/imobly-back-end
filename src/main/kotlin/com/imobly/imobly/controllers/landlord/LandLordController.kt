package com.imobly.imobly.controllers.landlord

import com.imobly.imobly.controllers.landlord.mappers.LandLordWebMapper
import com.imobly.imobly.controllers.landlord.dtos.LandLordDTO
import com.imobly.imobly.controllers.landlord.dtos.UpdateLandLordDTO
import com.imobly.imobly.controllers.landlord.dtos.UpdateLandLordEmailDTO
import com.imobly.imobly.services.ChangeEmailService
import com.imobly.imobly.services.LandLordService
import com.imobly.imobly.services.security.TokenService
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/locadores")
class LandLordController(
    val service: LandLordService,
    val changeEmailService: ChangeEmailService,
    val mapper: LandLordWebMapper,
    val tokenService: TokenService
) {
    @GetMapping("/encontrarperfil")
    fun findProfile(request: HttpServletRequest): ResponseEntity<LandLordDTO> {
        val id = tokenService.getIdFromRequest(request)
        return ResponseEntity.ok().body(mapper.toDTO(service.findById(id)))
    }

    @PutMapping("/atualizarperfil")
    fun updateProfile(request: HttpServletRequest, @Valid @RequestBody landlord: UpdateLandLordDTO): ResponseEntity<LandLordDTO> {
        val id = tokenService.getIdFromRequest(request)
        return ResponseEntity.ok().body(
            mapper.toDTO(service.updateAccount(id, mapper.toDomain(landlord)))
        )
    }

    @PatchMapping("/enviarcodigoparaatualizaremail")
    fun sendCodeForUpdateEmail(
        request: HttpServletRequest,
        @Valid @RequestBody landlord: UpdateLandLordEmailDTO
    ): ResponseEntity<LandLordDTO> {
        val id = tokenService.getIdFromRequest(request)
        changeEmailService.sendCodeForUpdateEmail( id, mapper.toDomain(landlord) )
        return ResponseEntity.ok().build()
    }

    @PatchMapping("/atualizaremail/{code}")
    fun sendCodeForUpdateEmail(request: HttpServletRequest, @PathVariable code: String): ResponseEntity<LandLordDTO> {
        val id = tokenService.getIdFromRequest(request)
        changeEmailService.updateEmail( id, code )
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/deletarperfil")
    fun deleteProfile(request: HttpServletRequest): ResponseEntity<Void> {
        val id = tokenService.getIdFromRequest(request)
        service.deleteAccount(id)
        return ResponseEntity.ok().build()
    }
}