package com.imobly.imobly.controllers.issuereport.dtos

import com.imobly.imobly.domains.enums.ReportStatusEnum
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class UpdateReportDTO(
    @field:NotNull(message = "O campo resposta é obrigatório")
    @field:Size(min = 10, max = 500, message = "O campo resposta deve ter entre 10  e 500 caracteres")
    val response: String?,

    @field:NotNull(message = "O campo status é obrigatório")
    val status: ReportStatusEnum?
)