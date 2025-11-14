package com.imobly.imobly.controllers.issuereport

import com.imobly.imobly.controllers.issuereport.dtos.ComplaintDTO
import com.imobly.imobly.controllers.issuereport.mappers.ReportWebMapper
import com.imobly.imobly.controllers.issuereport.dtos.ReportDTO
import com.imobly.imobly.controllers.issuereport.dtos.ResponseReportDTO
import com.imobly.imobly.controllers.issuereport.dtos.StatusReportDTO
import com.imobly.imobly.controllers.issuereport.mappers.ComplaintWebMapper
import com.imobly.imobly.controllers.issuereport.mappers.ResponseReportWebMapper
import com.imobly.imobly.controllers.issuereport.mappers.StatusReportWebMapper
import com.imobly.imobly.services.IssueReportService
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
    val service: IssueReportService,
    val reportMapper: ReportWebMapper,
    val complaintMapper: ComplaintWebMapper,
    val statusReportMapper: StatusReportWebMapper,
    val responseReportMapper: ResponseReportWebMapper
) {
    @GetMapping("/encontrartodos")
    fun findAllByTitleAndDescription(@RequestParam("titulooumensagem") titleOrMessage: String): ResponseEntity<List<ReportDTO>> =
        ResponseEntity.ok().body(reportMapper.toDTOs(service.findAllByTitleOrDescription(titleOrMessage)))

    @GetMapping("/encontrarporid/{id}")
    fun findById(@PathVariable id: String): ResponseEntity<ReportDTO> =
        ResponseEntity.ok().body(reportMapper.toDTO(service.findById(id)))

    @PostMapping("/inserir")
    fun insert(@Valid @RequestBody complaint: ComplaintDTO, ): ResponseEntity<ReportDTO> =
        ResponseEntity.status(HttpStatus.CREATED).body(
            reportMapper.toDTO(service.insert(complaintMapper.toDomain(complaint)))
        )

    @PatchMapping("/responderreportacao/{id}")
    fun replyToReport(
        @PathVariable id: String, @Valid @RequestBody response: ResponseReportDTO,
    ): ResponseEntity<ReportDTO> = ResponseEntity.ok().body(
        reportMapper.toDTO(service.replyToReport(id, responseReportMapper.toDomain(response)))
    )

    @PatchMapping("/atualizarstatus/{id}")
    fun updateStatus(
        @PathVariable id: String, @Valid @RequestBody status: StatusReportDTO,
    ): ResponseEntity<ReportDTO> = ResponseEntity.ok().body(
        reportMapper.toDTO(service.updateStatus(id, statusReportMapper.toDomain(status)))
    )

    @DeleteMapping("/deletar/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity.ok().build()
    }
}