package com.imobly.imobly.persistences.issuereport.mappers

import com.imobly.imobly.domains.reports.ReportDomain
import com.imobly.imobly.persistences.issuereport.entities.ReportEntity
import com.imobly.imobly.persistences.tenant.mappers.TenantPersistenceMapper
import org.springframework.stereotype.Component

@Component
class ReportPersistenceMapper(val tenantMapper: TenantPersistenceMapper) {
    fun toDomain(report: ReportEntity): ReportDomain =
        ReportDomain(
            report.id,
            report.title,
            report.message,
            report.moment,
            report.status,
            report.response,
            tenantMapper.toDomain(report.tenant)
        )

    fun toDomains(reports: List<ReportEntity>): List<ReportDomain> =
        reports.map { toDomain(it) }

    fun toEntity(report: ReportDomain): ReportEntity =
        ReportEntity(
            report.id,
            report.title,
            report.message,
            report.moment,
            report.status,
            report.response,
            tenantMapper.toEntity(report.tenant)
        )
}