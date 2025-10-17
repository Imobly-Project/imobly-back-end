package com.imobly.imobly.controllers.tenant.mappers

import com.imobly.imobly.controllers.tenant.dtos.TenantDTO
import com.imobly.imobly.domains.TenantDomain
import org.springframework.stereotype.Component

@Component
class TenantWebMapper {

    fun toDomain(tenant: TenantDTO): TenantDomain =
        TenantDomain(
            id = tenant.id,
            name = tenant.name,
            email = tenant.email,
            password = tenant.password,
            rg = tenant.rg,
            cpf = tenant.cpf,
            birthDate = tenant.birthDate,
            nationality = tenant.nationality,
            maritalStatus = tenant.maritalStatus,
            telephones = tenant.telephones.filter{it.isNotBlank()},
            pathImage = tenant.pathImage
        )

    fun toDTO(tenant: TenantDomain): TenantDTO =
        TenantDTO(
            id = tenant.id,
            name = tenant.name,
            email = tenant.email,
            password = tenant.password,
            rg = tenant.rg,
            cpf = tenant.cpf,
            birthDate = tenant.birthDate,
            nationality = tenant.nationality,
            maritalStatus = tenant.maritalStatus,
            telephones = tenant.telephones,
            pathImage = tenant.pathImage
        )

    fun toDTOs(tenants: List<TenantDomain>): List<TenantDTO>{
        return tenants.map {
            toDTO(it)
        }
    }
}