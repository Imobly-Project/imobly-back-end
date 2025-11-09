package com.imobly.imobly.services

import com.imobly.imobly.domains.reports.ComplaintDomain
import com.imobly.imobly.domains.reports.ReportDomain
import com.imobly.imobly.domains.reports.ResponseReportDomain
import com.imobly.imobly.domains.reports.StatusReportDomain
import com.imobly.imobly.domains.users.tenant.TenantDomain
import com.imobly.imobly.exceptions.ResourceNotFoundException
import com.imobly.imobly.exceptions.enums.RuntimeErrorEnum
import com.imobly.imobly.persistences.issuereport.mappers.ReportPersistenceMapper
import com.imobly.imobly.persistences.issuereport.repositories.ReportRepository
import com.imobly.imobly.persistences.tenant.repositories.TenantRepository
import org.springframework.stereotype.Service

@Service
class IssueReportService(
    private val reportRepository: ReportRepository,
    private val tenantRepository: TenantRepository,
    private val mapper: ReportPersistenceMapper
) {
    fun findAll(): List<ReportDomain> = mapper.toDomains(reportRepository.findAll())

    fun findById(id: String): ReportDomain =
        mapper.toDomain(reportRepository.findById(id).orElseThrow({
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0015)
        }))

    fun insert(complaint: ComplaintDomain): ReportDomain {
        tenantRepository.findById(complaint.tenantId).orElseThrow({
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0012)
        })
        val report = ReportDomain(
            title = complaint.title,
            message = complaint.message,
            tenant = TenantDomain(id = complaint.tenantId)
        )
        val reportSaved = reportRepository.save(mapper.toEntity(report))
        return mapper.toDomain(reportSaved)
    }

    fun replyToReport(id: String, responseReport: ResponseReportDomain): ReportDomain {
        val report = mapper.toDomain(reportRepository.findById(id).orElseThrow({
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0015)
        }))
        report.response = responseReport.response
        val reportUpdated = reportRepository.save(mapper.toEntity(report))
        return mapper.toDomain(reportUpdated)
    }

    fun updateStatus(id: String, statusReport: StatusReportDomain): ReportDomain {
        val report = mapper.toDomain(reportRepository.findById(id).orElseThrow({
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0015)
        }))
        report.status = statusReport.status
        val reportUpdated = reportRepository.save(mapper.toEntity(report))
        return mapper.toDomain(reportUpdated)
    }

    fun delete(id: String) {
        reportRepository.findById(id).orElseThrow({
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0015)
        })
        reportRepository.deleteById(id)
    }
}