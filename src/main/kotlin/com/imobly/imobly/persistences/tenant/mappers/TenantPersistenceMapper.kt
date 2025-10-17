package com.imobly.imobly.persistences.tenant.mappers

import com.imobly.imobly.domains.TenantDomain
import com.imobly.imobly.persistences.tenant.entities.TenantEntity

import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Component
class TenantPersistenceMapper {

    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    fun toDomain(tenant: TenantEntity): TenantDomain =
        TenantDomain(
            id = tenant.id,
            name = tenant.name,
            email = tenant.email,
            password = tenant.password,
            rg = tenant.rg,
            cpf = tenant.cpf,
            birthDate = tenant.birthDate.format(dateFormatter),
            nationality = tenant.nationality,
            maritalStatus = tenant.maritalStatus,
            telephones = tenant.telephones.split(",").map { it.trim()},
            pathImage = tenant.pathImage
        )

    fun toEntity(tenant: TenantDomain): TenantEntity =
        TenantEntity(
            id = tenant.id,
            name = tenant.name,
            email = tenant.email,
            password = tenant.password,
            rg = tenant.rg,
            cpf = tenant.cpf,
            birthDate = LocalDate.parse(tenant.birthDate, dateFormatter),
            nationality = tenant.nationality,
            maritalStatus = tenant.maritalStatus,
            telephones = tenant.telephones.joinToString(","),
            pathImage = tenant.pathImage
        )

    fun toDomains(tenants: List<TenantEntity>): List<TenantDomain>{
        return tenants.map{
            toDomain(it)
        }
    }
}