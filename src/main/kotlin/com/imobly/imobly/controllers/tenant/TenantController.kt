package com.imobly.imobly.controllers.tenant

import com.imobly.imobly.controllers.landlord.dtos.LandLordDTO
import com.imobly.imobly.controllers.landlord.dtos.UpdateLandLordEmailDTO
import com.imobly.imobly.controllers.tenant.dtos.LandLordUpdateTenantDTO
import com.imobly.imobly.controllers.tenant.dtos.SelfUpdateTenantDTO
import com.imobly.imobly.controllers.tenant.dtos.TenantDTO
import com.imobly.imobly.controllers.tenant.dtos.UpdateTenantEmailDTO
import com.imobly.imobly.controllers.tenant.mappers.TenantWebMapper
import com.imobly.imobly.services.TenantService
import com.imobly.imobly.services.changeemail.TenantChangeEmailService
import com.imobly.imobly.services.security.TokenService
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/locatarios")
class TenantController(
    val tenantService: TenantService,
    val changeEmailService: TenantChangeEmailService,
    val mapper: TenantWebMapper,
    val tokenService: TokenService
) {

    @GetMapping("/encontrarperfil")
    fun findProfile(request: HttpServletRequest): ResponseEntity<TenantDTO> {
        val id = tokenService.getIdFromRequest(request)
        return ResponseEntity.ok().body(mapper.toDTO(tenantService.findById(id)))
    }

    @PatchMapping("/atualizarperfil")
    fun updateProfile(
        request: HttpServletRequest,
        @Validated @RequestPart("tenant") tenant: SelfUpdateTenantDTO,
        @RequestPart(value = "file", required = false) file: MultipartFile?
    ): ResponseEntity<TenantDTO> {
        val id = tokenService.getIdFromRequest(request)
        return ResponseEntity.ok().body(
            mapper.toDTO(
                tenantService.selfUpdate( id, mapper.toDomain(tenant), file )
            )
        )
    }

    @PatchMapping("/enviarcodigoparaatualizaremail")
    fun sendCodeForUpdateEmail(
        request: HttpServletRequest,
        @Valid @RequestBody tenant: UpdateTenantEmailDTO
    ): ResponseEntity<TenantDTO> {
        val id = tokenService.getIdFromRequest(request)
        changeEmailService.sendCodeForUpdateEmail( id, mapper.toDomain(tenant) )
        return ResponseEntity.ok().build()
    }

    @PatchMapping("/atualizaremail/{code}")
    fun updateEmail(request: HttpServletRequest, @PathVariable code: String): ResponseEntity<TenantDTO> {
        val id = tokenService.getIdFromRequest(request)
        changeEmailService.updateEmail( id, code )
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/deletarperfil")
    fun deleteProfile(request: HttpServletRequest): ResponseEntity<Void> {
        val id = tokenService.getIdFromRequest(request)
        tenantService.delete(id)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/encontrartodos")
    fun findAllByNameAndCpf(@RequestParam("nomeoucpf") nameOrCpf: String): ResponseEntity<List<TenantDTO>> =
        ResponseEntity.ok().body(mapper.toDTOs(tenantService.findAllByNameOrCpf(nameOrCpf)))

    @PutMapping("/atualizar/{id}")
    fun updateByLandLord(
        @PathVariable id: String,
        @Validated @RequestPart("tenant") tenant: LandLordUpdateTenantDTO,
        @RequestPart(value = "file", required = false) file: MultipartFile?
    ): ResponseEntity<TenantDTO> = ResponseEntity.ok().body(
        mapper.toDTO(tenantService.landLordUpdate( id,mapper.toDomain(tenant), file ))
    )

    @DeleteMapping("/deletar/{id}")
    fun deleteByLandLord(@PathVariable id: String): ResponseEntity<Void> {
        tenantService.delete(id)
        return ResponseEntity.ok().build()
    }
}