package com.imobly.imobly.controllers.tenant

import com.imobly.imobly.controllers.tenant.dtos.LandLordUpdateTenantDTO
import com.imobly.imobly.controllers.tenant.dtos.SelfUpdateTenantDTO
import com.imobly.imobly.controllers.tenant.dtos.TenantDTO
import com.imobly.imobly.controllers.tenant.mappers.TenantWebMapper
import com.imobly.imobly.services.TenantService
import com.imobly.imobly.services.security.TokenService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/locatarios")
class TenantController(
    val service: TenantService,
    val mapper: TenantWebMapper,
    val tokenService: TokenService
) {

    @GetMapping("/encontrarperfil")
    fun findProfile(request: HttpServletRequest): ResponseEntity<TenantDTO> {
        val id = getIdFromRequest(request)
        return ResponseEntity.ok().body(mapper.toDTO(service.findById(id)))
    }

    @PatchMapping("/atualizarperfil")
    fun updateProfile(
        request: HttpServletRequest,
        @Validated @RequestPart("tenant") tenant: SelfUpdateTenantDTO,
        @RequestPart(value = "file", required = false) file: MultipartFile?
    ): ResponseEntity<TenantDTO> {
        val id = getIdFromRequest(request)
        return ResponseEntity.ok().body(
            mapper.toDTO(
                service.selfUpdate(id, mapper.toDomain(tenant), file)
            )
        )
    }

    @GetMapping("/encontrartodos")
    fun findAll(): ResponseEntity<List<TenantDTO>> =
        ResponseEntity.ok().body(mapper.toDTOs(service.findAll()))

    @PutMapping("/atualizar/{id}")
    fun updateByLandLord(
        @PathVariable id: String,
        @Validated @RequestPart("tenant") tenant: LandLordUpdateTenantDTO,
        @RequestPart(value = "file", required = false) file: MultipartFile?
    ): ResponseEntity<TenantDTO> = ResponseEntity.ok().body(
        mapper.toDTO(service.landLordUpdate(id,mapper.toDomain(tenant),file))
    )

    @DeleteMapping("/deletar/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity.ok().build()
    }

    private fun getIdFromRequest(request: HttpServletRequest): String {
        val token = tokenService.extractToken(request.getHeader("Authorization"))
        return tokenService.extractId(token)
    }
}