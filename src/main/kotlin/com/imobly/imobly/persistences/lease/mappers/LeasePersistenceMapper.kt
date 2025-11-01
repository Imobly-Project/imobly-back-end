package com.imobly.imobly.persistences.lease.mappers

import com.imobly.imobly.domains.leases.LeaseDomain
import com.imobly.imobly.persistences.category.mappers.CategoryPersistenceMapper
import com.imobly.imobly.persistences.lease.entities.LeaseEntity
import com.imobly.imobly.persistences.payment.mappers.PaymentPersistenceMapper
import com.imobly.imobly.persistences.property.mappers.PropertyPersistenceMapper
import com.imobly.imobly.persistences.tenant.mappers.TenantPersistenceMapper
import org.springframework.stereotype.Component

@Component
class LeasePersistenceMapper(
    val tenantMapper: TenantPersistenceMapper,
    val propertyMapper: PropertyPersistenceMapper,
) {

    fun toDomain(lease: LeaseEntity): LeaseDomain =
        LeaseDomain(
            id = lease.id,
            startDate = lease.startDate,
            endDate = lease.endDate,
            createdAt = lease.createdAt,
            lastUpdatedAt = lease.lastUpdatedAt,
            property = propertyMapper.toDomain(lease.property, CategoryPersistenceMapper()),
            tenant = tenantMapper.toDomain(lease.tenant),
            monthlyRent = lease.monthlyRent,
            securityDeposit = lease.securityDeposit,
            paymentDueDay = lease.paymentDueDay,
            isEnabled = lease.isEnabled
        )

    fun toEntity(lease: LeaseDomain): LeaseEntity =
        LeaseEntity(
            id = lease.id,
            startDate = lease.startDate,
            endDate = lease.endDate,
            createdAt = lease.createdAt,
            lastUpdatedAt = lease.lastUpdatedAt,
            property = propertyMapper.toEntity(lease.property, CategoryPersistenceMapper()),
            tenant = tenantMapper.toEntity(lease.tenant),
            monthlyRent = lease.monthlyRent,
            securityDeposit = lease.securityDeposit,
            paymentDueDay = lease.paymentDueDay,
            isEnabled = lease.isEnabled
        )

    fun toDomains(properties: List<LeaseEntity>): List<LeaseDomain> =
        properties.map {
            toDomain(it)
        }
}