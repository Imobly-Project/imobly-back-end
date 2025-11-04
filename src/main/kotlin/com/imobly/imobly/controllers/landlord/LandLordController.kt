package com.imobly.imobly.controllers.landlord

import com.imobly.imobly.controllers.landlord.mappers.LandLordWebMapper
import com.imobly.imobly.controllers.landlord.dtos.LandLordDTO
import com.imobly.imobly.services.LandLordService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/locadores")
class LandLordController(val service: LandLordService, val mapper: LandLordWebMapper) {

    @GetMapping("/encontrarporid/{id}")
    fun findById(@PathVariable id: String): ResponseEntity<LandLordDTO> =
        ResponseEntity.ok().body(mapper.toDTO(service.findById(id)))


    @PutMapping("/atualizar/{id}")
    fun update(
        @PathVariable id: String, @Valid @RequestBody landlord: LandLordDTO,
    ): ResponseEntity<LandLordDTO> = ResponseEntity.ok().body(
        mapper.toDTO(service.update(id, mapper.toDomain(landlord)))
    )

    @DeleteMapping("/deletar/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity.ok().build()
    }
}