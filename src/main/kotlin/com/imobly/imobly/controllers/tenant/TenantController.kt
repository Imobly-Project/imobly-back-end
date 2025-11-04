package com.imobly.imobly.controllers.tenant

import com.imobly.imobly.controllers.tenant.dtos.TenantDTO
import com.imobly.imobly.controllers.tenant.mappers.TenantWebMapper
import com.imobly.imobly.services.TenantService
import jakarta.validation.constraints.NotNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/locatarios")
class TenantController(val service: TenantService, val mapper: TenantWebMapper) {

    @GetMapping("/encontrartodos")
    fun findAll(): ResponseEntity<List<TenantDTO>> =
        ResponseEntity.ok().body(
            mapper.toDTOs(service.findAll())
        )

    @GetMapping("/encontrarporid/{id}")
    fun findById(@PathVariable id: String): ResponseEntity<TenantDTO> =
        ResponseEntity.ok().body(
            mapper.toDTO(service.findById(id))
        )

    @PutMapping("/atualizar/{id}")
    fun update(
        @PathVariable id: String,
        @Validated @RequestPart("tenant") tenant: TenantDTO,
        @RequestPart(value = "file", required = false) file: MultipartFile?
    ): ResponseEntity<TenantDTO> = ResponseEntity.ok().body(
        mapper.toDTO(service.update(id,mapper.toDomain(tenant),file))
    )

    @DeleteMapping("/deletar/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity.ok().build()
    }
}