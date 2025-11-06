package com.imobly.imobly.controllers.tenant.mappers

import com.imobly.imobly.controllers.property.dtos.AddressDTO
import com.imobly.imobly.controllers.property.mappers.AddressWebMapper
import com.imobly.imobly.controllers.tenant.dtos.RestrictedTenantDTO
import com.imobly.imobly.controllers.tenant.dtos.TelephoneDTO
import com.imobly.imobly.controllers.tenant.dtos.TenantDTO
import com.imobly.imobly.domains.users.TenantDomain
import com.imobly.imobly.domains.enums.MaritalStatusEnum
import com.imobly.imobly.domains.users.RestrictedTenantDomain
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class RestrictedTenantWebMapper() {

    fun toDomain(tenant: RestrictedTenantDTO): RestrictedTenantDomain =
        RestrictedTenantDomain(
            email = (tenant.email ?: "").trim(),
            telephones = listOf(
                tenant.telephones?.telephone1?.trim() ?: "",
                tenant.telephones?.telephone2?.trim() ?: "",
                tenant.telephones?.telephone3?.trim() ?: ""
            ),
        )
}