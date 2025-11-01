package com.imobly.imobly.controllers.issuereport.mappers

import com.imobly.imobly.controllers.issuereport.dtos.StatusReportDTO
import com.imobly.imobly.domains.reports.StatusReportDomain
import com.imobly.imobly.domains.enums.ReportStatusEnum
import org.springframework.stereotype.Component

@Component
class StatusReportWebMapper {
    fun toDomain(statusReport: StatusReportDTO): StatusReportDomain =
        StatusReportDomain(statusReport.status ?: ReportStatusEnum.PENDING)

    fun toDTO(statusReport: StatusReportDomain): StatusReportDTO =
        StatusReportDTO(statusReport.status)
}