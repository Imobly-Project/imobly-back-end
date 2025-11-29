package com.imobly.imobly.services

import com.imobly.imobly.domains.ReportDomain
import com.imobly.imobly.domains.users.TenantDomain
import com.imobly.imobly.exceptions.OperationNotAllowedException
import com.imobly.imobly.exceptions.ResourceNotFoundException
import com.imobly.imobly.exceptions.enums.RuntimeErrorEnum
import com.imobly.imobly.persistences.issuereport.entities.ReportEntity
import com.imobly.imobly.persistences.issuereport.mappers.ReportPersistenceMapper
import com.imobly.imobly.persistences.issuereport.repositories.ReportRepository
import com.imobly.imobly.persistences.lease.repositories.LeaseRepository
import com.imobly.imobly.persistences.property.repositories.PropertyRepository
import com.imobly.imobly.persistences.tenant.repositories.TenantRepository
import org.springframework.stereotype.Service
import java.util.Collections

@Service
class IssueReportService(
    private val reportRepository: ReportRepository,
    private val tenantRepository: TenantRepository,
    private val leaseRepository: LeaseRepository,
    private val propertyRepository: PropertyRepository,
    private val mapper: ReportPersistenceMapper
) {
    fun findByTenantIdAndTitleOrMessage(tenantId: String, titleOrMessage: String): List<ReportDomain> {
        val list = mapper.toDomains(
            reportRepository.findByTenant_IdAndTitleContainingIgnoreCaseOrTenant_IdAndMessageContainingIgnoreCase(tenantId, titleOrMessage, tenantId, titleOrMessage)
        )
        Collections.sort(list)
        return list
    }

    fun findAllByTitleOrMessage(titleOrMessage: String): List<ReportDomain> {
        val list = mapper.toDomains(
            reportRepository.findByTitleContainingOrMessageContainingAllIgnoreCase(titleOrMessage, titleOrMessage)
        )
        Collections.sort(list)
        return list
    }

    fun findById(id: String): ReportDomain =
        mapper.toDomain(reportRepository.findById(id).orElseThrow({
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0015)
        }))

    fun create(report: ReportDomain, tenantId: String): ReportDomain {
        if (!tenantRepository.existsById(tenantId))
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0012)
        if (!propertyRepository.existsById(report.property.id ?: ""))
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0011)
        if (!leaseRepository.existsByTenant_IdAndProperty_Id(tenantId, report.property.id!!))
            throw OperationNotAllowedException(RuntimeErrorEnum.ERR0024)
        report.tenant = TenantDomain(id = tenantId)
        val reportSaved = reportRepository.save(mapper.toEntity(report))
        return mapper.toDomain(reportSaved)
    }

    fun update(id: String, report: ReportDomain): ReportDomain {
        val reportFound = mapper.toDomain(reportRepository.findById(id).orElseThrow {
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0015)
        })
        reportFound.response = report.response
        reportFound.status = report.status
        val reportUpdated = reportRepository.save(mapper.toEntity(reportFound))
        return mapper.toDomain(reportUpdated)
    }

    fun delete(id: String) {
        if (!reportRepository.existsById(id))
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0015)

        reportRepository.deleteById(id)
    }
}