package com.imobly.imobly.controllers.property

import com.imobly.imobly.controllers.property.dtos.PropertyDTO
import com.imobly.imobly.controllers.property.mappers.PropertyWebMapper
import com.imobly.imobly.services.PropertyService
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/propriedades")
class PropertyController(
    val service: PropertyService,
    val mapper: PropertyWebMapper
) {

    @GetMapping("/encontrartodos")
    fun findAll(): ResponseEntity<List<PropertyDTO>> {
        return ResponseEntity.ok().body(mapper.toDTOs(service.findAll()))
    }

    @GetMapping("/encontrarporid/{id}")
    fun findById(@PathVariable id: String): ResponseEntity<PropertyDTO> {
        return ResponseEntity.ok().body(mapper.toDTO(service.findById(id)))
    }

    @PostMapping("/inserir")
    fun insert(
        @Valid @RequestPart(value = "property") property: PropertyDTO,
        @Valid @NotNull(message = "A imagem deve ser enviada.") @RequestPart(value = "files") files: List<MultipartFile>
    ): ResponseEntity<PropertyDTO> {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            mapper.toDTO(service.insert(mapper.toDomain(property), files))
        )
    }

    @PutMapping("/atualizar/{id}")
    fun update(
        @PathVariable id: String,
        @Valid @RequestPart(value = "property") property: PropertyDTO,
        @Valid @NotNull(message = "A imagem deve ser enviada.") @RequestPart(value = "files") files: List<MultipartFile>
    ): ResponseEntity<PropertyDTO> {
        return ResponseEntity.ok().body(
            mapper.toDTO(service.update(id, mapper.toDomain(property), files))
        )
    }

    @DeleteMapping("/deletar/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity.ok().build()
    }
}