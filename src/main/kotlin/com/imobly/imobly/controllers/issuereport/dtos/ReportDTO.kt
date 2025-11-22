package com.imobly.imobly.controllers.issuereport.dtos

import com.fasterxml.jackson.annotation.JsonFormat
import com.imobly.imobly.controllers.property.dtos.PropertyDTO
import com.imobly.imobly.controllers.tenant.dtos.TenantDTO
import com.imobly.imobly.domains.enums.ReportStatusEnum
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDate
import java.time.LocalDateTime

data class ReportDTO(
    val id: String?,

    val title: String?,

    val message: String?,

    @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    val moment: LocalDateTime?,

    val status: ReportStatusEnum?,

    val response: String?,

    val tenant: TenantDTO?,

    val property: PropertyDTO?
)