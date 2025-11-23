package com.imobly.imobly.controllers.category

import com.imobly.imobly.controllers.category.mappers.CategoryWebMapper
import com.imobly.imobly.controllers.category.dtos.CategoryDTO
import com.imobly.imobly.controllers.property.mappers.AddressWebMapper
import com.imobly.imobly.controllers.property.mappers.PropertyWebMapper
import com.imobly.imobly.services.CategoryService
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
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/categorias")
class CategoryController(
    val service: CategoryService, val mapper: CategoryWebMapper
) {
    @GetMapping("/encontrartodos")
    fun findAllByTitle(@RequestParam("titulo") title: String): ResponseEntity<List<CategoryDTO>> =
        ResponseEntity.ok().body(
            mapper.toDTOs(service.findAllByTitle(title))
        )

    @GetMapping("/encontrarporid/{id}")
    fun findById(@PathVariable id: String): ResponseEntity<CategoryDTO> =
        ResponseEntity.ok().body(
            mapper.toDTO(service.findById(id), PropertyWebMapper(AddressWebMapper()))
        )

    @PostMapping("/criar")
    fun insert(@Valid @RequestBody category: CategoryDTO): ResponseEntity<CategoryDTO> =
        ResponseEntity.status(HttpStatus.CREATED).body(
            mapper.toDTO(
                service.insert(mapper.toDomain(category)),
                PropertyWebMapper(AddressWebMapper())
            )
        )

    @PutMapping("/atualizar/{id}")
    fun update(
        @PathVariable id: String, @Valid @RequestBody category: CategoryDTO,
    ): ResponseEntity<CategoryDTO> = ResponseEntity.ok().body(
        mapper.toDTO(
            service.update(id,mapper.toDomain(category)),
            PropertyWebMapper(AddressWebMapper())
        )
    )

    @DeleteMapping("/deletar/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity.ok().build()
    }
}