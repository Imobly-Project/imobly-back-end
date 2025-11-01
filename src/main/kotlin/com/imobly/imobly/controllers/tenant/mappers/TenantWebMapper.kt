package com.imobly.imobly.controllers.tenant.mappers

import com.imobly.imobly.controllers.property.dtos.AddressDTO
import com.imobly.imobly.controllers.property.mappers.AddressWebMapper
import com.imobly.imobly.controllers.tenant.dtos.TelephoneDTO
import com.imobly.imobly.controllers.tenant.dtos.TenantDTO
import com.imobly.imobly.domains.users.TenantDomain
import com.imobly.imobly.domains.enums.MaritalStatusEnum
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class TenantWebMapper(val addressMapper: AddressWebMapper) {
    fun toDomainOnlyId(tenant: TenantDTO): TenantDomain =
        TenantDomain(id = tenant.id)

    fun toDomain(tenant: TenantDTO): TenantDomain =
        TenantDomain(
            firstName = (tenant.firstName ?: "").trim(),
            lastName = (tenant.lastName ?: "").trim(),
            email = (tenant.email ?: "").trim(),
            password = (tenant.password ?: "").trim(),
            rg = tenant.rg ?: "",
            cpf = tenant.cpf ?: "",
            job = tenant.job ?: "",
            birthDate = tenant.birthDate ?: LocalDate.of(2000, 1, 1),
            nationality = (tenant.nationality ?: "").trim(),
            maritalStatus = tenant.maritalStatus ?: MaritalStatusEnum.SINGLE,
            telephones = tenant.telephones?.map { it.telephone } ?: emptyList(),
            address = addressMapper.toDomain(tenant.address ?: AddressDTO())
        )

    fun toDTO(tenant: TenantDomain): TenantDTO =
        TenantDTO(
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
            telephones = tenant.telephones.map { TelephoneDTO(it) },
            pathImage = tenant.pathImage,
            address = addressMapper.toDTO(tenant.address)
        )

    fun toDTOs(tenants: List<TenantDomain>): List<TenantDTO> =
        tenants.map {
            toDTO(it)
        }
}