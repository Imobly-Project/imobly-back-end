package com.imobly.imobly.persistences.tenant.mappers

import com.imobly.imobly.domains.users.RegisteredUserDomain
import com.imobly.imobly.domains.users.TenantDomain
import com.imobly.imobly.persistences.property.mappers.AddressPersistenceMapper
import com.imobly.imobly.persistences.tenant.entities.TenantEntity
import org.springframework.stereotype.Component

@Component
class TenantPersistenceMapper(val mapperAddress: AddressPersistenceMapper) {

    fun toDomain(tenant: TenantEntity): TenantDomain =
        TenantDomain(
            id = tenant.id,
            firstName = tenant.firstName,
            lastName = tenant.lastName,
            email = tenant.email,
            password = tenant.password,
            rg = tenant.rg,
            cpf = tenant.cpf,
            job = tenant.job,
            birthDate = tenant.birthDate,
            nationality = tenant.nationality,
            maritalStatus = tenant.maritalStatus,
            telephones = tenant.telephones,
            pathImage = tenant.pathImage,
            address = mapperAddress.toDomain(tenant.address),
            role = tenant.role
        )

    fun toEntity(tenant: TenantDomain): TenantEntity =
        TenantEntity(
            id = tenant.id,
            firstName = tenant.firstName,
            lastName = tenant.lastName,
            email = tenant.email,
            password = tenant.password,
            rg = tenant.rg,
            cpf = tenant.cpf,
            job = tenant.job,
            birthDate = tenant.birthDate,
            nationality = tenant.nationality,
            maritalStatus = tenant.maritalStatus,
            telephones = tenant.telephones,
            pathImage = tenant.pathImage,
            address = mapperAddress.toEntity(tenant.address),
            role = tenant.role
        )

    fun toDomains(tenants: List<TenantEntity>): List<TenantDomain> =
        tenants.map{
            toDomain(it)
        }

    fun toRegisteredUserDomain(tenant: TenantEntity): RegisteredUserDomain =
        RegisteredUserDomain(
            tenant.id,
            tenant.firstName,
            tenant.lastName,
            tenant.email,
            tenant.telephones,
            tenant.password,
            tenant.role
        )
}