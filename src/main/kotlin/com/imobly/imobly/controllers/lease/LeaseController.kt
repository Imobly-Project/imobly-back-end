package com.imobly.imobly.controllers.lease

import com.imobly.imobly.controllers.lease.dtos.LeaseAgreementDTO
import com.imobly.imobly.controllers.lease.dtos.LeaseAgreementUpdateDTO
import com.imobly.imobly.controllers.lease.dtos.LeaseDTO
import com.imobly.imobly.controllers.lease.mappers.LeaseAgreementWebMapper
import com.imobly.imobly.controllers.lease.mappers.LeaseWebMapper
import com.imobly.imobly.services.LeaseService
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
@RequestMapping("/locacoes")
class LeaseController(
    val service: LeaseService,
    val leaseMapper: LeaseWebMapper,
    val leaseAgreement: LeaseAgreementWebMapper
) {

    @GetMapping("/encontrartodos")
    fun findAll(): ResponseEntity<List<LeaseDTO>> =
        ResponseEntity.ok().body(leaseMapper.toDTOs(service.findAll()))

    @GetMapping("/encontrarporid/{id}")
    fun findById(@PathVariable id: String): ResponseEntity<LeaseDTO> =
        ResponseEntity.ok().body(leaseMapper.toDTO(service.findById(id)))

    @PostMapping("/inserir")
    fun insert(@Valid @RequestBody lease: LeaseAgreementDTO): ResponseEntity<LeaseDTO> =
        ResponseEntity.status(HttpStatus.CREATED).body(
            leaseMapper.toDTO(service.insert(leaseAgreement.toDomain(lease)))
        )

    @PatchMapping("/atualizar/{id}")
    fun update(
        @PathVariable id: String, @Valid @RequestBody lease: LeaseAgreementUpdateDTO,
    ): ResponseEntity<LeaseDTO> = ResponseEntity.ok().body(
        leaseMapper.toDTO(service.update(id, leaseAgreement.toDomain(lease)))
    )

    @PatchMapping("/alternarativo/{id}")
    fun toggleEnable(@PathVariable id: String): ResponseEntity<Void> {
        service.toggleEnable(id)
        return ResponseEntity.ok().build()
    }
}