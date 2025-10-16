package com.imobly.imobly.controllers.tenant

import com.imobly.imobly.controllers.tenant.dtos.TenantDTO
import com.imobly.imobly.exceptions.ResourceNotFoundException
import com.imobly.imobly.controllers.tenant.mappers.TenantWebMapper
import com.imobly.imobly.services.TenantService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/locatarios")
class TenantController(
    val service: TenantService,
    val mapper: TenantWebMapper
) {

    @GetMapping("/encontrartodos")
    fun findAll(): ResponseEntity<List<TenantDTO>> {
        return ResponseEntity.ok().body(mapper.toDTOs(service.findAll()))
    }

    @GetMapping("/encontrarporid/{id}")
    fun findById(@PathVariable id: String): ResponseEntity<TenantDTO> {
        return ResponseEntity.ok().body(mapper.toDTO(service.findById(id)))
    }

    @PostMapping("/inserir")
    fun insert(
        @Validated @RequestBody tenant: TenantDTO
    ): ResponseEntity<TenantDTO> {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            mapper.toDTO(service.insert(mapper.toDomain(tenant))))
    }

    @PutMapping("/atualizar/{id}")
    fun update(@PathVariable id: String, @RequestBody tenant: TenantDTO): ResponseEntity<TenantDTO> {
        return ResponseEntity.ok().body(mapper.toDTO(service.update(id,mapper.toDomain(tenant))))
    }

    @DeleteMapping("/deletar/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity.ok().build()
    }
}