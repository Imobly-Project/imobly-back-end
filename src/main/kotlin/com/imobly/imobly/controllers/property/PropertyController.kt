package com.imobly.imobly.controllers.property

import com.imobly.imobly.controllers.category.mappers.CategoryWebMapper
import com.imobly.imobly.controllers.property.dtos.PropertyDTO
import com.imobly.imobly.controllers.property.mappers.PropertyWebMapper
import com.imobly.imobly.services.PropertyService
import com.imobly.imobly.services.security.TokenService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/propriedades")
class PropertyController(
    private val service: PropertyService,
    private val mapper: PropertyWebMapper,
    private val tokenService: TokenService
) {

    @GetMapping("/encontrartodos")
    fun findAllByTitle(@RequestParam("titulo") title: String): ResponseEntity<List<PropertyDTO>> =
        ResponseEntity.ok().body(mapper.toDTOs(service.findAllByTitle(title)))

    @GetMapping("/encontrarporperfil")
    fun findByTenantIdAndTitle(@RequestParam("titulo") title: String, request: HttpServletRequest): ResponseEntity<List<PropertyDTO>> {
        val id = tokenService.getIdFromRequest(request)
        return ResponseEntity.ok().body(mapper.toDTOs(service.findByTenantIdAndTitle(title, id)))
    }

    @GetMapping("/encontrarporid/{id}")
    fun findById(@PathVariable id: String): ResponseEntity<PropertyDTO> =
        ResponseEntity.ok().body(mapper.toDTO(service.findById(id), CategoryWebMapper()))

    @PostMapping("/inserir")
    fun insert(
        @Validated
        @RequestPart(value = "property") property: PropertyDTO,
        @RequestPart(value = "files") files: List<MultipartFile>?
    ): ResponseEntity<PropertyDTO> = ResponseEntity.status(HttpStatus.CREATED).body(
        mapper.toDTO(
            service.insert(mapper.toDomain(property, CategoryWebMapper()), files),
            CategoryWebMapper()
        )
    )

    @PutMapping("/atualizar/{id}")
    fun update(
        @PathVariable id: String,
        @Validated @RequestPart(value = "property") property: PropertyDTO,
        @RequestPart(value = "files", required = false) files: List<MultipartFile>?
    ): ResponseEntity<PropertyDTO> = ResponseEntity.ok().body(
        mapper.toDTO(
            service.update(id, mapper.toDomain(property, CategoryWebMapper()), files),
            CategoryWebMapper()
        )
    )

    @DeleteMapping("/deletar/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity.ok().build()
    }
}