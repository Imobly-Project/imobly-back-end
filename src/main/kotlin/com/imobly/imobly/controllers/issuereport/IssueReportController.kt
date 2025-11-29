package com.imobly.imobly.controllers.issuereport

import com.imobly.imobly.controllers.issuereport.dtos.TenantCreateReportDTO
import com.imobly.imobly.controllers.issuereport.mappers.ReportWebMapper
import com.imobly.imobly.controllers.issuereport.dtos.ReportDTO
import com.imobly.imobly.controllers.issuereport.dtos.UpdateReportDTO
import com.imobly.imobly.services.IssueReportService
import com.imobly.imobly.services.security.TokenService
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/reportacoes")
class IssueReportController(
    private val service: IssueReportService,
    private val mapper: ReportWebMapper,
    private val tokenService: TokenService
) {

    @GetMapping("/encontrarporperfil")
    fun findByTenantIdAndTitleOrMessage(
        @RequestParam("titulooumensagem") titleOrMessage: String,
        request: HttpServletRequest
    ): ResponseEntity<List<ReportDTO>> {
        val tenantId = tokenService.getIdFromRequest(request)
        return ResponseEntity.ok().body(
            mapper.toDTOs(service.findByTenantIdAndTitleOrMessage(tenantId, titleOrMessage))
        )
    }

    @GetMapping("/encontrartodos")
    fun findAllByTitleOrMessage(@RequestParam("titulooumensagem") titleOrMessage: String): ResponseEntity<List<ReportDTO>> =
        ResponseEntity.ok().body(
            mapper.toDTOs(service.findAllByTitleOrMessage(titleOrMessage))
        )

    @GetMapping("/encontrarporid/{id}")
    fun findById(@PathVariable id: String): ResponseEntity<ReportDTO> =
        ResponseEntity.ok().body(
            mapper.toDTO(service.findById(id))
        )

    @PostMapping("/criar")
    fun create(
        @Valid @RequestBody report: TenantCreateReportDTO,
        request: HttpServletRequest
    ): ResponseEntity<ReportDTO> {
        val tenantId = tokenService.getIdFromRequest(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            mapper.toDTO(service.create(mapper.toDomain(report), tenantId))
        )
    }

    @PatchMapping("/atualizar/{id}")
    fun update(
        @PathVariable id: String, @Valid @RequestBody response: UpdateReportDTO,
    ): ResponseEntity<ReportDTO> = ResponseEntity.ok().body(
        mapper.toDTO(service.update(id, mapper.toDomain(response)))
    )

    @DeleteMapping("/deletar/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity.ok().build()
    }
}