package com.imobly.imobly.controllers.issuereport.mappers

import com.imobly.imobly.controllers.issuereport.dtos.ResponseReportDTO
import com.imobly.imobly.domains.reports.ResponseReportDomain
import org.springframework.stereotype.Component

@Component
class ResponseReportWebMapper {
    fun toDomain(responseReport: ResponseReportDTO): ResponseReportDomain =
        ResponseReportDomain(responseReport.response ?: "")

    fun toDTO(responseReport: ResponseReportDomain): ResponseReportDTO =
        ResponseReportDTO(responseReport.response)
}