package com.imobly.imobly.controllers.issuereport.mappers

import com.imobly.imobly.controllers.category.mappers.CategoryWebMapper
import com.imobly.imobly.controllers.issuereport.dtos.TenantCreateReportDTO
import com.imobly.imobly.controllers.issuereport.dtos.ReportDTO
import com.imobly.imobly.controllers.issuereport.dtos.UpdateReportDTO
import com.imobly.imobly.controllers.property.mappers.PropertyWebMapper
import com.imobly.imobly.controllers.tenant.mappers.TenantWebMapper
import com.imobly.imobly.domains.ReportDomain
import com.imobly.imobly.domains.enums.ReportStatusEnum
import org.springframework.stereotype.Component

@Component
class ReportWebMapper(
    val tenantMapper: TenantWebMapper,
    val propertyMapper: PropertyWebMapper,
    val categoryMapper: CategoryWebMapper
) {
    fun toDomain(report: TenantCreateReportDTO): ReportDomain =
        ReportDomain(
            title = report.title ?: "",
            message = report.message ?: "",
            property = propertyMapper.toDomainOnlyId(report.propertyId ?: "")
        )

    fun toDomain(report: UpdateReportDTO): ReportDomain =
        ReportDomain(
            status = report.status ?: ReportStatusEnum.PENDING,
            response = report.response ?: ""
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
            tenantMapper.toDTO(report.tenant),
            propertyMapper.toDTO(report.property, categoryMapper)
        )
}