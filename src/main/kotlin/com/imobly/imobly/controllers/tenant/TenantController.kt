package com.imobly.imobly.controllers.tenant

import com.imobly.imobly.controllers.tenant.dtos.RestrictedTenantDTO
import com.imobly.imobly.controllers.tenant.dtos.TenantDTO
import com.imobly.imobly.controllers.tenant.mappers.RestrictedTenantWebMapper
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
    val tenantMapper: TenantWebMapper,
    val tokenService: TokenService,
    val restrictedTenantMapper: RestrictedTenantWebMapper
) {

    @GetMapping("/encontrarperfil")
    fun findProfile(request: HttpServletRequest): ResponseEntity<TenantDTO> {
        val id = getIdFromRequest(request)
        return ResponseEntity.ok().body(tenantMapper.toDTO(service.findById(id)))
    }

    @PatchMapping("/atualizarperfil")
    fun updateProfile(
        request: HttpServletRequest,
        @Validated @RequestPart("tenant") tenant: RestrictedTenantDTO,
        @RequestPart(value = "file", required = false) file: MultipartFile?
    ): ResponseEntity<TenantDTO> {
        val id = getIdFromRequest(request)
        return ResponseEntity.ok().body(
            tenantMapper.toDTO(
                service.restrictedUpdate(id, restrictedTenantMapper.toDomain(tenant), file)
            )
        )
    }

    @GetMapping("/encontrartodos")
    fun findAll(): ResponseEntity<List<TenantDTO>> =
        ResponseEntity.ok().body(tenantMapper.toDTOs(service.findAll()))

    @PutMapping("/atualizar/{id}")
    fun update(
        @PathVariable id: String,
        @Validated @RequestPart("tenant") tenant: TenantDTO,
        @RequestPart(value = "file", required = false) file: MultipartFile?
    ): ResponseEntity<TenantDTO> = ResponseEntity.ok().body(
        tenantMapper.toDTO(service.update(id,tenantMapper.toDomain(tenant),file))
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