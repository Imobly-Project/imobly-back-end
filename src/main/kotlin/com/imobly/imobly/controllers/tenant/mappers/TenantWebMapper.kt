package com.imobly.imobly.controllers.tenant.mappers

import com.imobly.imobly.controllers.property.dtos.AddressDTO
import com.imobly.imobly.controllers.property.mappers.AddressWebMapper
import com.imobly.imobly.controllers.tenant.dtos.LandLordUpdateTenantDTO
import com.imobly.imobly.controllers.tenant.dtos.SelfUpdateTenantDTO
import com.imobly.imobly.controllers.tenant.dtos.TelephoneDTO
import com.imobly.imobly.controllers.tenant.dtos.TenantDTO
import com.imobly.imobly.domains.users.TenantDomain
import com.imobly.imobly.domains.enums.MaritalStatusEnum
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class TenantWebMapper(val addressMapper: AddressWebMapper) {

    fun toDomain(tenant: LandLordUpdateTenantDTO): TenantDomain =
        TenantDomain(
            firstName = (tenant.firstName ?: "").trim(),
            lastName = (tenant.lastName ?: "").trim(),
            email = (tenant.email ?: "").trim(),
            rg = (tenant.rg ?: "").trim(),
            cpf = (tenant.cpf ?: "").trim(),
            job = (tenant.job ?: "").trim(),
            birthDate = tenant.birthDate ?: LocalDate.of(2000, 1, 1),
            nationality = (tenant.nationality ?: "").trim(),
            maritalStatus = tenant.maritalStatus ?: MaritalStatusEnum.SINGLE,
            telephones = listOf(
                tenant.telephones?.telephone1?.trim() ?: "",
                tenant.telephones?.telephone2?.trim() ?: "",
                tenant.telephones?.telephone3?.trim() ?: ""
            ),
            address = addressMapper.toDomain(tenant.address ?: AddressDTO())
        )

    fun toDomain(tenant: SelfUpdateTenantDTO): TenantDomain =
        TenantDomain(
            email = (tenant.email ?: "").trim(),
            telephones = listOf(
                tenant.telephones?.telephone1?.trim() ?: "",
                tenant.telephones?.telephone2?.trim() ?: "",
                tenant.telephones?.telephone3?.trim() ?: ""
            )
        )

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
            telephones = listOf(
                tenant.telephones?.telephone1?.trim() ?: "",
                tenant.telephones?.telephone2?.trim() ?: "",
                tenant.telephones?.telephone3?.trim() ?: ""
            ),
            address = addressMapper.toDomain(tenant.address ?: AddressDTO())
        )

    fun toDTO(tenant: TenantDomain): TenantDTO =
        TenantDTO(
            id = tenant.id,
            firstName = tenant.firstName,
            lastName = tenant.lastName,
            email = tenant.email,
            password = "",
            rg = tenant.rg,
            cpf = tenant.cpf,
            job = tenant.job,
            birthDate = tenant.birthDate,
            nationality = tenant.nationality,
            maritalStatus = tenant.maritalStatus,
            telephones = TelephoneDTO(
                tenant.telephones.getOrNull(0),
                tenant.telephones.getOrNull(1),
                tenant.telephones.getOrNull(2)
            ),
            pathImage = tenant.pathImage,
            address = addressMapper.toDTO(tenant.address),
            role = tenant.role
        )

    fun toDTOs(tenants: List<TenantDomain>): List<TenantDTO> =
        tenants.map {
            toDTO(it)
        }
}