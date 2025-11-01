package com.imobly.imobly.controllers.issuereport.mappers

import com.imobly.imobly.controllers.issuereport.dtos.ReportDTO
import com.imobly.imobly.controllers.tenant.dtos.TenantDTO
import com.imobly.imobly.controllers.tenant.mappers.TenantWebMapper
import com.imobly.imobly.domains.reports.ReportDomain
import com.imobly.imobly.domains.enums.ReportStatusEnum
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class ReportWebMapper(val tenantMapper: TenantWebMapper) {
    fun toDomain(report: ReportDTO): ReportDomain =
        ReportDomain(
            report.id,
            report.title ?: "",
            report.message ?: "",
            report.moment ?: LocalDateTime.now(),
            report.status ?: ReportStatusEnum.NEW,
            report.response ?: "",
            tenantMapper.toDomainOnlyId(report.tenant ?: TenantDTO())
        )

    fun toDTOs(reports: List<ReportDomain>): List<ReportDTO> =
        reports.map { toDTO(it) }

    fun toDTO(report: ReportDomain): ReportDTO =
        ReportDTO(
            report.id,
            report.title,
            report.message,
            report.moment,
            report.status,
            report.response,
            tenantMapper.toDTO(report.tenant)
        )
}