package com.imobly.imobly.controllers.lease.mappers

import com.imobly.imobly.controllers.category.mappers.CategoryWebMapper
import com.imobly.imobly.controllers.lease.dtos.LeaseDTO
import com.imobly.imobly.controllers.payment.mappers.PaymentWebMapper
import com.imobly.imobly.controllers.property.dtos.PropertyDTO
import com.imobly.imobly.controllers.property.mappers.PropertyWebMapper
import com.imobly.imobly.controllers.tenant.dtos.TenantDTO
import com.imobly.imobly.controllers.tenant.mappers.TenantWebMapper
import com.imobly.imobly.domains.leases.LeaseDomain
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalDateTime

@Component
class LeaseWebMapper(val propertyMapper: PropertyWebMapper, val tenantMapper: TenantWebMapper) {

    fun toDomain(lease: LeaseDTO): LeaseDomain =
        LeaseDomain(
            startDate = lease.startDate ?: LocalDate.now(),
            endDate = lease.endDate ?: LocalDate.now().plusYears(1),
            createdAt = lease.createdAt ?: LocalDateTime.now(),
            lastUpdatedAt = lease.lastUpdatedAt ?: LocalDateTime.now(),
            property = propertyMapper.toDomain(lease.property ?: PropertyDTO(), CategoryWebMapper()),
            tenant = tenantMapper.toDomain(lease.tenant ?: TenantDTO()),
            durationInMonths = lease.durationInMonths ?: 0,
            monthlyRent = lease.monthlyRent ?: 0.0,
            securityDeposit = lease.securityDeposit ?: 0.0,
            paymentDueDay = lease.paymentDueDay ?: 1,
        )

    fun toDTO(lease: LeaseDomain): LeaseDTO =
        LeaseDTO(
            id = lease.id,
            startDate = lease.startDate,
            endDate = lease.endDate,
            createdAt = lease.createdAt,
            lastUpdatedAt = lease.lastUpdatedAt,
            property = propertyMapper.toDTO(lease.property, CategoryWebMapper()),
            tenant = tenantMapper.toDTO(lease.tenant),
            durationInMonths = lease.durationInMonths,
            monthlyRent = lease.monthlyRent,
            securityDeposit = lease.securityDeposit,
            paymentDueDay = lease.paymentDueDay,
            isEnabled = lease.isEnabled
        )

    fun toDTOs(leases: List<LeaseDomain>): List<LeaseDTO> =
        leases.map { toDTO(it) }
}