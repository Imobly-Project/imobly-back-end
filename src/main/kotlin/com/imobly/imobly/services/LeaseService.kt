package com.imobly.imobly.services

import com.imobly.imobly.domains.leases.LeaseAgreementDomain
import com.imobly.imobly.domains.leases.LeaseDomain
import com.imobly.imobly.domains.PropertyDomain
import com.imobly.imobly.domains.users.tenant.TenantDomain
import com.imobly.imobly.exceptions.InvalidArgumentsException
import com.imobly.imobly.exceptions.ResourceNotFoundException
import com.imobly.imobly.exceptions.enums.RuntimeErrorEnum
import com.imobly.imobly.persistences.lease.mappers.LeasePersistenceMapper
import com.imobly.imobly.persistences.lease.repositories.LeaseRepository
import com.imobly.imobly.persistences.property.repositories.PropertyRepository
import com.imobly.imobly.persistences.tenant.repositories.TenantRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class LeaseService(
    private val leaseRepository: LeaseRepository,
    private val propertyRepository: PropertyRepository,
    private val tenantRepository: TenantRepository,
    private val paymentService: PaymentService,
    private val mapper: LeasePersistenceMapper
) {
    fun findAll(): List<LeaseDomain> = mapper.toDomains(leaseRepository.findAll())

    fun findById(id: String): LeaseDomain =
        mapper.toDomain(leaseRepository.findById(id).orElseThrow {
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0016)
        })


    fun insert(leaseAgreement: LeaseAgreementDomain): LeaseDomain {
        propertyRepository.findById(leaseAgreement.propertyId).orElseThrow({
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0011)
        })
        tenantRepository.findById(leaseAgreement.tenantId).orElseThrow({
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0012)
        })
        if (leaseAgreement.endDate.isBefore(leaseAgreement.startDate))
            throw InvalidArgumentsException(RuntimeErrorEnum.ERR0002)
        val lease = LeaseDomain(
            startDate = leaseAgreement.startDate,
            endDate = leaseAgreement.endDate,
            property = PropertyDomain(id = leaseAgreement.propertyId),
            tenant = TenantDomain(id = leaseAgreement.tenantId),
            monthlyRent = leaseAgreement.monthlyRent,
            securityDeposit = leaseAgreement.securityDeposit,
            paymentDueDay = leaseAgreement.paymentDueDay
        )
        val leaseSaved = leaseRepository.save(mapper.toEntity(lease))
        paymentService.insert(mapper.toDomain(leaseSaved))
        return mapper.toDomain(leaseSaved)
    }

    fun update(id: String, leaseWithNewData: LeaseAgreementDomain): LeaseDomain {
        val lease = mapper.toDomain(leaseRepository.findById(id).orElseThrow({
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0016)
        }))
        if (leaseWithNewData.endDate.isBefore(leaseWithNewData.startDate))
            throw InvalidArgumentsException(RuntimeErrorEnum.ERR0002)
        lease.startDate = leaseWithNewData.startDate
        lease.endDate = leaseWithNewData.endDate
        lease.monthlyRent = leaseWithNewData.monthlyRent
        lease.paymentDueDay = leaseWithNewData.paymentDueDay
        lease.securityDeposit = leaseWithNewData.securityDeposit
        lease.lastUpdatedAt = LocalDateTime.now()
        val leaseUpdated = leaseRepository.save(mapper.toEntity(lease))
        return mapper.toDomain(leaseUpdated)
    }

    fun toggleEnable(id: String) {
        val lease = mapper.toDomain(leaseRepository.findById(id).orElseThrow({
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0016)
        }))
        lease.isEnabled = !lease.isEnabled
        leaseRepository.save(mapper.toEntity(lease))
    }
}