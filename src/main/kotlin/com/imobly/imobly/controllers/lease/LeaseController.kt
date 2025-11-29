package com.imobly.imobly.controllers.lease

import com.imobly.imobly.controllers.lease.dtos.CreateLeaseDTO
import com.imobly.imobly.controllers.lease.dtos.UpdateLeaseDTO
import com.imobly.imobly.controllers.lease.dtos.LeaseDTO
import com.imobly.imobly.controllers.lease.mappers.LeaseWebMapper
import com.imobly.imobly.services.LeaseService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/locacoes")
class LeaseController(val service: LeaseService, val mapper: LeaseWebMapper) {

    @GetMapping("/encontrartodos")
    fun findAllByTenantNameOrPropertyTitleAndIsEnabled(
        @RequestParam("nomeoutitulo") nameOrTitle: String,
        @RequestParam("active", defaultValue = "true", required = false) active: Boolean
    ): ResponseEntity<List<LeaseDTO>> =
        ResponseEntity.ok().body(mapper.toDTOs(service.findAllByTenantNameOrPropertyTitleAndIsEnabled(nameOrTitle, active)))

    @GetMapping("/encontrarporid/{id}")
    fun findById(@PathVariable id: String): ResponseEntity<LeaseDTO> =
        ResponseEntity.ok().body(mapper.toDTO(service.findById(id)))

    @PostMapping("/inserir")
    fun insert(@Valid @RequestBody lease: CreateLeaseDTO): ResponseEntity<LeaseDTO> =
        ResponseEntity.status(HttpStatus.CREATED).body(
            mapper.toDTO(service.insert(mapper.toDomain(lease)))
        )

    @PatchMapping("/atualizar/{id}")
    fun update(
        @PathVariable id: String, @Valid @RequestBody lease: UpdateLeaseDTO,
    ): ResponseEntity<LeaseDTO> = ResponseEntity.ok().body(
        mapper.toDTO(service.update(id, mapper.toDomain(lease)))
    )

    @PatchMapping("/alternarativo/{id}")
    fun toggleEnable(@PathVariable id: String): ResponseEntity<Void> {
        service.toggleEnable(id)
        return ResponseEntity.ok().build()
    }
}