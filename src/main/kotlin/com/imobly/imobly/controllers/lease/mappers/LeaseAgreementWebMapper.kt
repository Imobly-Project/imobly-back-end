package com.imobly.imobly.controllers.lease.mappers

import com.imobly.imobly.controllers.lease.dtos.LeaseAgreementDTO
import com.imobly.imobly.controllers.lease.dtos.LeaseAgreementUpdateDTO
import com.imobly.imobly.domains.leases.LeaseAgreementDomain
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class LeaseAgreementWebMapper {
    fun toDomain(lease: LeaseAgreementDTO): LeaseAgreementDomain =
        LeaseAgreementDomain(
            startDate = lease.startDate ?: LocalDate.now(),
            endDate = lease.endDate ?: LocalDate.now().plusYears(1),
            propertyId = lease.propertyId ?: "",
            tenantId = lease.tenantId ?: "",
            monthlyRent = lease.monthlyRent ?: 0.0,
            securityDeposit = lease.securityDeposit ?: 0.0,
            paymentDueDay = lease.paymentDueDay ?: 1
        )

    fun toDomain(lease: LeaseAgreementUpdateDTO): LeaseAgreementDomain =
        LeaseAgreementDomain(
            startDate = lease.startDate ?: LocalDate.now(),
            endDate = lease.endDate ?: LocalDate.now().plusYears(1),
            propertyId = "",
            tenantId = "",
            monthlyRent = lease.monthlyRent ?: 0.0,
            securityDeposit = lease.securityDeposit ?: 0.0,
            paymentDueDay = lease.paymentDueDay ?: 1
        )

    fun toDTO(lease: LeaseAgreementDomain): LeaseAgreementDTO =
        LeaseAgreementDTO(
            startDate = lease.startDate,
            endDate = lease.endDate,
            propertyId = lease.propertyId,
            tenantId = lease.tenantId,
            monthlyRent = lease.monthlyRent,
            securityDeposit = lease.securityDeposit,
            paymentDueDay = lease.paymentDueDay
        )

    fun toDTOs(leases: List<LeaseAgreementDomain>): List<LeaseAgreementDTO> =
        leases.map {
            toDTO(it)
        }
}