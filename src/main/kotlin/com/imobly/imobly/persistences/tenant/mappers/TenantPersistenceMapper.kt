package com.imobly.imobly.persistences.tenant.mappers

import com.imobly.imobly.domains.TenantDomain
import com.imobly.imobly.persistences.tenant.entities.TenantEntity
import com.imobly.imobly.persistences.tenant.entities.UserRegisteredEntity
import org.springframework.stereotype.Component

@Component
object TenantPersistenceMapper {
    fun toDomain(tenant: TenantEntity): TenantDomain =
        TenantDomain(
            id = tenant.id,
            name = tenant.name,
            email = tenant.email,
            password = tenant.password,
            rg = tenant.rg,
            cpf = tenant.cpf,
            birthDate = tenant.birthDate,
            nationality = tenant.nationality,
            maritalStatus = tenant.maritalStatus
        )

    fun toEntity(tenant: TenantDomain): TenantEntity =
        TenantEntity(
            id = tenant.id,
            name = tenant.name,
            email = tenant.email,
            password = tenant.password,
            rg = tenant.rg,
            cpf = tenant.cpf,
            birthDate = tenant.birthDate,
            nationality = tenant.nationality,
            maritalStatus = tenant.maritalStatus
        )

    fun toDomains(tenants: List<TenantEntity>): List<TenantDomain>{
        return tenants.map{
            toDomain(it)
        }
    }
}